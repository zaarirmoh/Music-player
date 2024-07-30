package com.example.musicplayer

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.audio.PlayBackService
import com.example.musicplayer.ui.theme.MusicPlayerTheme

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
        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                //Surface(modifier = Modifier.fillMaxSize()){
                    Spacer(modifier = Modifier.padding(innerPadding))

                    //GetStartedScreen()
                    //ChooseThemeScreen()
                    //HomeScreen()
                    val intent = Intent(this, PlayBackService::class.java)
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            startService(intent)
                        }) {
                            Text(text = "Play")
                        }
                    }
                    //startForegroundService(intent)
                    //MusicPlayerPager()
                    /*
                    val viewModel1 = PermissionsViewModel()
                    val audioViewModel = AudioViewModel()
                    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            viewModel1.onPermissionResult(
                                permission = Manifest.permission.POST_NOTIFICATIONS,
                                isGranted = isGranted
                            )
                        }
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            requestPermissionLauncher.launch(
                                Manifest.permission.POST_NOTIFICATIONS
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




