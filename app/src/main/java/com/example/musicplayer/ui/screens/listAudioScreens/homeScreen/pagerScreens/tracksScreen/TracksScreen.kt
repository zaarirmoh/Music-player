package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.tracksScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.audio.AudioViewModel
import com.example.musicplayer.audio.PlayBackService
import com.example.musicplayer.ui.majorComponents.AudioController
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
    controllerFuture: ListenableFuture<MediaController>,
    isPlaying: MutableState<Boolean>,
    isServiceStarted: MutableState<Boolean>,
    collectNewAudioFile: MutableState<Boolean>,
    currentAudioSelected: MutableIntState,
){
    val coroutineScope = rememberCoroutineScope()
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
        currentAudioSelected = currentAudioSelected,
        onAudioFileClicked = {
            if(!isServiceStarted.value){
                context.startService(intent)
                isServiceStarted.value = true
            }
            controllerFuture.addListener({
                val mediaController = controllerFuture.get()
                if(mediaController.currentMediaItemIndex != it){
                    mediaController.seekTo(it, 0)
                    mediaController.prepare()
                    mediaController.play()
                    isPlaying.value = true
                    collectNewAudioFile.value = !collectNewAudioFile.value
                }else if (mediaController.isPlaying){
                    mediaController.pause()
                    isPlaying.value = false
                }else{
                    mediaController.play()
                    isPlaying.value = true
                }
            }, MoreExecutors.directExecutor())
        }
    )
}