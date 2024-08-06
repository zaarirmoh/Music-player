package com.example.musicplayer.ui.majorComponents

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.audio.AudioFile

@Composable
fun AudioItem(
    modifier: Modifier = Modifier,
    audioFile: AudioFile,
    audioFileIndex: Int,
    bitmap: Bitmap?,
    onAudioFileClicked: (Int) -> Unit,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height((52 + 20).dp)
            .clickable { onAudioFileClicked(audioFileIndex) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(18.dp))
        AudioItemPhoto(bitmap =bitmap)
        Spacer(modifier = Modifier.width(18.dp))
        Column {
            Text(
                text = if(audioFile.title.length < 26) audioFile.title else audioFile.title.substring(0,25)+"...",
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                )
            }

        }

    }
}
