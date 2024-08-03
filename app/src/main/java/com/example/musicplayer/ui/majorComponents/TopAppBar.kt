package com.example.musicplayer.ui.majorComponents

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.ui.theme.chopsicFontFamily

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onTracksClicked: () -> String,
    onAlbumsClicked: () -> String,
    onPlaylistsClicked: () -> String,
    onArtistsClicked: () -> String,
    onFavoritesClicked: () -> String
){
    var pageSelected: String by remember { mutableStateOf("Tracks") }
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainer)
    ){
        Column(
            modifier = modifier
        ) {
            Spacer(modifier = modifier.height(65.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = modifier.width(8.dp))
                Text(
                    text = "Music  Player",
                    fontFamily = chopsicFontFamily,
                    fontSize = 22.sp,
                    modifier = modifier.padding(start = 15.dp)
                )
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = modifier.width(7.dp))
                }
            }
            Spacer(modifier = modifier.height(50.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = { pageSelected = onTracksClicked() }) {
                    Text(
                        text = "Tracks",
                        color = if(pageSelected == "Tracks") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        fontWeight = if(pageSelected == "Tracks") FontWeight.Bold else null,
                        fontSize = if(pageSelected == "Tracks") 19.sp else TextUnit.Unspecified
                    )
                }
                TextButton(onClick = { pageSelected = onAlbumsClicked() }) {
                    Text(
                        text = "Albums",
                        color = if(pageSelected == "Albums") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        fontWeight = if(pageSelected == "Albums") FontWeight.Bold else null,
                        fontSize = if(pageSelected == "Albums") 19.sp else TextUnit.Unspecified
                    )
                }
                TextButton(onClick = { pageSelected = onPlaylistsClicked() }) {
                    Text(
                        text = "Playlists",
                        color = if(pageSelected == "Playlists") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        fontWeight = if(pageSelected == "Playlists") FontWeight.Bold else null,
                        fontSize = if(pageSelected == "Playlists") 19.sp else TextUnit.Unspecified
                    )
                }
                TextButton(onClick = { pageSelected = onArtistsClicked() }) {
                    Text(
                        text = "Artists",
                        color = if(pageSelected == "Artists") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        fontWeight = if(pageSelected == "Artists") FontWeight.Bold else null,
                        fontSize = if(pageSelected == "Artists") 19.sp else TextUnit.Unspecified
                    )
                }
                TextButton(onClick = { pageSelected = onFavoritesClicked() }) {
                    Text(
                        text = "Favorites",
                        color = if(pageSelected == "Favorites") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        fontWeight = if(pageSelected == "Favorites") FontWeight.Bold else null,
                        fontSize = if(pageSelected == "Favorites") 19.sp else TextUnit.Unspecified
                    )
                }

                /*
                // isShuffle just for now i will change it later
                var isShuffle: Boolean by remember { mutableStateOf(false) }
                IconButton(onClick = { isShuffle = !isShuffle }) {
                    Icon(
                        imageVector = Icons.Filled.Shuffle,
                        contentDescription = null,
                        tint = if(isShuffle) MaterialTheme.colorScheme.primary else  LocalContentColor.current
                    )
                }
                 */

            }
        }
    }
}