package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.albumsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.ui.majorComponents.DisplayAlbumArt
import com.example.musicplayer.ui.theme.AlbumArtShapes

@Composable
fun AlbumItem(
    modifier: Modifier = Modifier,
    albumAudioFiles: List<AudioFile>,
    onAlbumItemClicked: () -> Unit = {},
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DisplayAlbumArt(
            bitmap = albumAudioFiles[0].albumArt,
            shape = AlbumArtShapes.large,
            size = 180.dp,
            onAlbumArtClicked = onAlbumItemClicked,
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = if(albumAudioFiles[0].album.length < 14) albumAudioFiles[0].album else albumAudioFiles[0].album.substring(0,13)+"...",
            fontSize = 15.sp,
            maxLines = 1,
        )
        Text(
            text = albumAudioFiles[0].artist + " | " + "${albumAudioFiles.size} tracks",
            fontSize = 12.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.outline
        )
    }
}