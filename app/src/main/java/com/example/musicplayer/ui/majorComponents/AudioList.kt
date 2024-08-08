package com.example.musicplayer.ui.majorComponents

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.audio.AudioFile

@Composable
fun AudioList(
    modifier: Modifier = Modifier,
    audioFiles: List<AudioFile>,
    onAudioFileClicked: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 70.dp)
    ) {
        itemsIndexed(audioFiles){ audioIndex,audioFile ->
            AudioItem(
                audioFile = audioFile,
                audioFileIndex = audioIndex,
                onAudioFileClicked = onAudioFileClicked,
                bitmap = audioFile.albumArt
            )
            Log.d(audioFile.title,audioFile.toString())
            HorizontalDivider(
                modifier = modifier.padding(start = 81.dp, end = 9.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}


