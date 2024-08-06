package com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.tracksScreen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.musicplayer.audio.AudioViewModel
import com.example.musicplayer.audio.PlayBackService
import com.example.musicplayer.ui.majorComponents.AudioList
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TracksScreen(
    modifier: Modifier = Modifier,
    audioViewModel: AudioViewModel,
    intent: Intent,
    context: Context,
    controllerFuture: ListenableFuture<MediaController>
){
    val coroutineScope = rememberCoroutineScope()
    val isServiceStarted = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val gson = Gson()
            val audioFilesJson = gson.toJson(audioViewModel.audioFiles.value)
            intent.apply {
                putExtra(PlayBackService.AUDIO_FILES, audioFilesJson)
            }
        }
    }

    AudioList(
        audioFiles = audioViewModel.audioFiles.collectAsState().value,
        onAudioFileClicked = {
            if(!isServiceStarted.value){
                context.startService(intent)
                isServiceStarted.value = true
            }
            controllerFuture.addListener({
                val mediaController = controllerFuture.get()
                if(mediaController.isPlaying){
                    mediaController.pause()
                }else if (mediaController.currentMediaItemIndex == it){
                    mediaController.play()
                }else{
                    mediaController.seekTo(it, 0)
                    mediaController.prepare()
                    mediaController.play()
                }
            }, MoreExecutors.directExecutor())
        }
    )
}
