package com.example.musicplayer

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicplayer.permissions.PermissionsViewModel
import com.example.musicplayer.trialLogic.AudioFile
import com.example.musicplayer.trialLogic.AudioViewModel
import com.example.musicplayer.ui.screens.getStartedScreen.GetStartedScreen
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val SHOW_WELCOME_SCREEN = booleanPreferencesKey("show_welcome_screen")

class MainActivity : ComponentActivity() {
    val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicPlayerViewModel::class.java)) {
                // Pass the required parameter to the LotokViewModel constructor
                return MusicPlayerViewModel(context = applicationContext) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    private val viewModel by viewModels<MusicPlayerViewModel>{ viewModelFactory }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }
        enableEdgeToEdge()
        setContent {
            MusicPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Spacer(modifier = Modifier.padding(innerPadding))
                    GetStartedScreen()
                    /*
                    val viewModel = viewModel<PermissionsViewModel>()
                    val audioViewModel = viewModel<AudioViewModel>()
                    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            viewModel.onPermissionResult(
                                permission = Manifest.permission.READ_MEDIA_AUDIO,
                                isGranted = isGranted
                            )
                        }
                    )
                     */
                    /*
                    val audioFiles = remember { mutableStateListOf<AudioFile>() }
                    val coroutineScope = rememberCoroutineScope()

                    LaunchedEffect(Unit) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val files = audioViewModel.loadAudioFiles(contentResolver)
                            audioFiles.addAll(files)
                        }
                    }

                    AudioList(audioFiles) { audioFile ->
                        // Handle audio file click, e.g., start playback
                    }
                     */
                    /*
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            requestPermissionLauncher.launch(
                                android.Manifest.permission.READ_MEDIA_AUDIO
                                ) }) {
                            Text(text = "Request permissions")
                        }
                    }
                     */
                }
            }
        }
    }
}
@Composable
fun AudioList(audioFiles: List<AudioFile>, onAudioFileClick: (AudioFile) -> Unit) {
    LazyColumn {
        items(audioFiles){ audioFile ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAudioFileClick(audioFile) }
                    .padding(16.dp)
            ) {
                Text(text = audioFile.title, style = MaterialTheme.typography.bodyMedium)
                Text(text = audioFile.artist, style = MaterialTheme.typography.bodyMedium)
                Text(text = audioFile.album, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


