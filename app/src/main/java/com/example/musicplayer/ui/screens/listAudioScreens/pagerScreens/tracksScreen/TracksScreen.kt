package com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.tracksScreen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.audio.AudioViewModel
import com.example.musicplayer.audio.AudioViewModelOld
import com.example.musicplayer.audio.PlayBackService
import com.example.musicplayer.ui.majorComponents.AudioList
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TracksScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    intent: Intent,
){
    val audioViewModel: AudioViewModel = viewModel()
    val audioFiles = remember { mutableStateListOf<AudioFile>() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    var startAudio by remember { mutableStateOf(true)}
    var effectFinished by remember { mutableStateOf(false) }
    val player: Player = ExoPlayer.Builder(context).build()
    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val files = audioViewModel.loadAudioFiles(contentResolver)
            audioFiles.addAll(files)
            val audioFilesJson = Gson().toJson(audioFiles)
            intent.apply {
                putExtra(PlayBackService.AUDIO_FILES,audioFilesJson)
            }
            effectFinished = true
        }
    }
    if(effectFinished){
        audioViewModel.addMediaItems(player,audioFiles)
        AudioList(
            audioFiles = audioFiles,
            paddingValues = paddingValues,
            onAudioFileClicked = {
                if(startAudio){
                    audioViewModel.playAudio(player,it)
                    intent.apply {
                        putExtra(PlayBackService.AUDIO_INDEX,it)
                    }
                    context.startService(intent)
                    startAudio = false
                }else{
                    audioViewModel.pauseAudio(player)
                    startAudio = true
                }
            }
        )
    }
}
