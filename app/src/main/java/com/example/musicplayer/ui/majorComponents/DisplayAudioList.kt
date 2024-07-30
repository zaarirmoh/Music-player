package com.example.musicplayer.ui.majorComponents

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.audio.AudioFile

@Composable
fun AudioList(
    modifier: Modifier = Modifier,
    audioFiles: List<AudioFile>,
    onAudioFileClicked: (AudioFile) -> Unit = {},
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = modifier.padding(paddingValues).padding(top = 10.dp),
    ) {
        items(audioFiles){ audioFile ->
            AudioItem(
                audioFile = audioFile,
                onAudioFileClicked = onAudioFileClicked,
                bitmap = audioFile.albumArt
            )
            Log.d(audioFile.title,audioFile.toString())
            Spacer(modifier = modifier.height(5.dp))
            HorizontalDivider(
                modifier = modifier.padding(start = 78.dp, end = 9.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier = modifier.height(5.dp))
        }
    }
}

@Composable
fun AudioItem(
    modifier: Modifier = Modifier,
    audioFile: AudioFile,
    onAudioFileClicked: (AudioFile) -> Unit,
    bitmap: Bitmap?,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onAudioFileClicked(audioFile) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        AudioItemPhoto(bitmap =bitmap)
        Spacer(modifier = Modifier.width(15.dp))
        Column(

        ) {
            Text(
                text = if(audioFile.title.length < 29) audioFile.title else audioFile.title.substring(0,28)+"...",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                maxLines = 1
            )
            Text(
                text = audioFile.artist,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                modifier = Modifier
                    .clickable {}
            )
        }

    }
}
@Composable
fun AudioItemPhoto(
    modifier: Modifier = Modifier,
    bitmap: Bitmap?
){
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier.size(48.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Album Art",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        } else {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null
                )
            }
        }

    }
}
