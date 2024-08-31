package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.albumsScreen

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.audio.AudioViewModel

@OptIn(UnstableApi::class)
@Composable
fun AlbumsScreen(
    modifier: Modifier = Modifier,
    audioViewModel: AudioViewModel,
){
    val audioFiles by audioViewModel.audioFiles.collectAsState()
    val albumNames = audioFiles.map { it.album }.distinct().sorted()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(190.dp),
        contentPadding = PaddingValues(10.dp),
        modifier = modifier.fillMaxSize().padding(bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(albumNames){ albumName ->
            val albumAudioFiles = audioFiles.filter { it.album == albumName }
            AlbumItem(albumAudioFiles = albumAudioFiles)
        }
    }
}