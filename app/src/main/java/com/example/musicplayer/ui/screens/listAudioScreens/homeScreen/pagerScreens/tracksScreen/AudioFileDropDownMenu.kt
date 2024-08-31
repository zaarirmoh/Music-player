package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.tracksScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReadMore
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.AlbumArtShapes

@Composable
fun AudioFileDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean> = mutableStateOf(false),
){
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        Card(
            shape = AlbumArtShapes.large,
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Text(text = "Add")
                Spacer(modifier = modifier.width(40.dp))
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Text(text = "Delete")
                Spacer(modifier = modifier.width(40.dp))
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Text(text = "Share")
                Spacer(modifier = modifier.width(40.dp))
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Text(text = "Track details")
                Spacer(modifier = modifier.width(40.dp))
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Text(text = "Album")
                Spacer(modifier = modifier.width(40.dp))
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Text(text = "Artist")
                Spacer(modifier = modifier.width(40.dp))
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Text(text = "Set as")
                Spacer(modifier = modifier.width(40.dp))
            }
        }
    }
}
@Preview
@Composable
fun AudioFileDropDownMenuPreview(){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val expanded = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { expanded.value = true }) {
                Text(text = "Click here")
            }
            AudioFileDropDownMenu(expanded = expanded)
        }
    }
}