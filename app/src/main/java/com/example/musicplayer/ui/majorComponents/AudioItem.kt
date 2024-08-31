package com.example.musicplayer.ui.majorComponents

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.tracksScreen.AudioFileDropDownMenu

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AudioItem(
    modifier: Modifier = Modifier,
    audioFile: AudioFile,
    audioFileIndex: Int,
    currentAudioSelected: MutableIntState,
    onAudioFileClicked: (Int) -> Unit,
    onAlbumArtClicked: (Int) -> Unit = {},
    onAudioFileLongClicked: (Int) -> Unit = {},
    onAudioItemMoreClicked: () -> Unit = {}
){
    val expanded = remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height((52 + 20).dp)
            .combinedClickable(
                onLongClick = {
                    onAudioFileLongClicked(audioFileIndex)
                },
                onClick = {
                    currentAudioSelected.intValue = audioFileIndex
                    onAudioFileClicked(audioFileIndex)
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(18.dp))
        DisplayAlbumArt(
            bitmap = audioFile.albumArt,
            onAlbumArtClicked = { onAlbumArtClicked(audioFileIndex) },
        )
        Spacer(modifier = Modifier.width(18.dp))
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if(audioFile.title.length < 26) audioFile.title else audioFile.title.substring(0,25)+"...",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                fontWeight = if(currentAudioSelected.intValue == audioFileIndex) FontWeight.Bold else null,
                color =
                    if(currentAudioSelected.intValue == audioFileIndex) MaterialTheme.colorScheme.error
                    else Color.Unspecified,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = audioFile.artist,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if(currentAudioSelected.intValue == audioFileIndex) FontWeight.Bold else null,
                color =
                    if(currentAudioSelected.intValue == audioFileIndex) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = {
                expanded.value = true
                Log.d("expended value",expanded.value.toString())
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                )
            }
            AudioFileDropDownMenu()
        }

    }
}
