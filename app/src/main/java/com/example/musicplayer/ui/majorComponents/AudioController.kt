package com.example.musicplayer.ui.majorComponents

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.ui.theme.Shapes

@Composable
fun AudioController(
    modifier: Modifier = Modifier,
    audioFile: AudioFile,
    isPlaying: Boolean = false,
    onPreviousClicked: () -> Unit = {},
    onPlayPauseClicked: () -> Unit = {},
    onNextClicked: () -> Unit = {},
    onShuffleClicked: () -> Unit = {}
){
    val artWorkPalette = audioFile.albumArt?.let { Palette.from(it).generate() }
    val dominantArtWorkColor: Color =
        artWorkPalette?.getDominantColor(MaterialTheme.colorScheme.secondaryContainer.toArgb())
            ?.let { Color(it) } ?: MaterialTheme.colorScheme.secondaryContainer
    val vibrantArtWorkColor: Color =
        artWorkPalette?.getVibrantColor(MaterialTheme.colorScheme.secondaryContainer.toArgb())
            ?.let { Color(it) } ?: MaterialTheme.colorScheme.secondaryContainer
    val darkVibrantArtWorkColor: Color =
        artWorkPalette?.getDarkVibrantColor(MaterialTheme.colorScheme.secondaryContainer.toArgb())
            ?.let { Color(it) } ?: MaterialTheme.colorScheme.secondaryContainer
    val lightVibrantArtWorkColor: Color =
        artWorkPalette?.getLightVibrantColor(MaterialTheme.colorScheme.secondaryContainer.toArgb())
            ?.let { Color(it) } ?: MaterialTheme.colorScheme.secondaryContainer
    val mutedArtWorkColor: Color =
        artWorkPalette?.getMutedColor(MaterialTheme.colorScheme.secondaryContainer.toArgb())
            ?.let { Color(it) } ?: MaterialTheme.colorScheme.secondaryContainer
    val darkMutedArtWorkColor: Color =
        artWorkPalette?.getDarkMutedColor(MaterialTheme.colorScheme.secondaryContainer.toArgb())
            ?.let { Color(it) } ?: MaterialTheme.colorScheme.secondaryContainer
    val lightMutedArtWorkColor: Color =
        artWorkPalette?.getLightMutedColor(MaterialTheme.colorScheme.secondaryContainer.toArgb())
            ?.let { Color(it) } ?: MaterialTheme.colorScheme.secondaryContainer
    Card(
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containerColor = mutedArtWorkColor),
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
    ){
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(12.dp))
                AudioItemPhoto(
                    bitmap = audioFile.albumArt,
                    size = 45.dp,
                    shape = Shapes.medium
                )
                Spacer(modifier = modifier.width(15.dp))
                Box(
                    //modifier = modifier.width(180.dp)
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(0.9f, false)

                ) {
                    SlidingText(
                        text = audioFile.title,
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    AudioControls(
                        onPreviousClicked = onPreviousClicked,
                        onPlayPauseClicked = onPlayPauseClicked,
                        onNextClicked = onNextClicked,
                        onShuffleClicked = onShuffleClicked,
                        isPlaying = isPlaying
                    )
                    Spacer(modifier = modifier.width(3.dp))
                }

            }
        }
    }
}
@Composable
fun SlidingText(
    modifier: Modifier = Modifier,
    text: String,
){
    Text(
        text = text,
        modifier = modifier.basicMarquee(),
        style = MaterialTheme.typography.bodyLarge,
        fontSize = 18.sp,
        maxLines = 1
    )
}
@Composable
fun AudioControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    onPreviousClicked: () -> Unit = {},
    onPlayPauseClicked: () -> Unit = {},
    onNextClicked: () -> Unit = {},
    onShuffleClicked: () -> Unit = {}
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onPreviousClicked) {
            Icon(
                imageVector = Icons.Rounded.SkipPrevious,
                contentDescription = null
            )
        }
        IconButton(onClick = onPlayPauseClicked) {
            Icon(
                imageVector = if(isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                contentDescription = null
            )
        }
        IconButton(onClick = onNextClicked) {
            Icon(
                imageVector = Icons.Rounded.SkipNext,
                contentDescription = null
            )
        }
        IconButton(onClick = onShuffleClicked) {
            Icon(
                imageVector = Icons.Rounded.Shuffle,
                contentDescription = null
            )
        }
    }
}



