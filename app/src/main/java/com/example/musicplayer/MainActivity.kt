package com.example.musicplayer

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicplayer.permissions.PermissionsViewModel
import com.example.musicplayer.trialLogic.AudioFile
import com.example.musicplayer.trialLogic.AudioViewModel
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

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
        enableEdgeToEdge()
        setContent {
            MusicPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Spacer(modifier = Modifier.padding(innerPadding))
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


