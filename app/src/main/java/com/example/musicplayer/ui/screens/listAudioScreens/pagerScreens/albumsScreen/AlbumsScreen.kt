package com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.albumsScreen

import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import com.example.musicplayer.audio.AudioViewModel
import com.example.musicplayer.ui.notSureComponent.PlayerView
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

@OptIn(UnstableApi::class)
@Composable
fun AlbumsScreen(
    modifier: Modifier = Modifier,
    audioViewModel: AudioViewModel,
    intent: Intent,
    context: Context,
    controllerFuture: ListenableFuture<MediaController>
){


}