package com.example.musicplayer.ui.majorComponents

import android.support.v7.graphics.Palette
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.ui.theme.Shapes

@Composable
fun AudioController(
    modifier: Modifier = Modifier,
    audioFile: AudioFile
){
    val artWorkPalette = audioFile.albumArt?.let { Palette.from(it).generate() }
    val dominantArtWorkColor: Color =
        artWorkPalette?.getDominantColor(MaterialTheme.colorScheme.primaryContainer.toArgb())
            ?.let { Color(it) } ?: MaterialTheme.colorScheme.primaryContainer
    Card(
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containerColor = dominantArtWorkColor),
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
       Row {
           Spacer(modifier = modifier.width(10.dp))
           AudioItemPhoto(
               bitmap = audioFile.albumArt,
               size = 40.dp,
               shape = Shapes.medium
           )
       }
    }
}