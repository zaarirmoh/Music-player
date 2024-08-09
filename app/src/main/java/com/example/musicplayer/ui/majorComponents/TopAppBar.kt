package com.example.musicplayer.ui.majorComponents

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.ui.theme.chopsicFontFamily

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    currentPage: Int,
    onSearchClicked: () -> Unit,
    onMenuClicked: () -> Unit,
    onTracksClicked: () -> String,
    onAlbumsClicked: () -> String,
    onPlaylistsClicked: () -> String,
    onArtistsClicked: () -> String,
    onFavoritesClicked: () -> String,
    dropDownMenu: @Composable () -> Unit = {},
){
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
    ){
        Column(
            modifier = modifier
        ) {
            Spacer(modifier = modifier.height(65.dp))
            FirstTopAppBarRow(
                onSearchClicked = onSearchClicked,
                onMenuClicked = onMenuClicked,
                dropDownMenu = dropDownMenu
            )
            Spacer(modifier = modifier.height(40.dp))
            SecondTopAppBarRow(
                currentPage = currentPage,
                onTracksClicked = onTracksClicked,
                onAlbumsClicked = onAlbumsClicked,
                onPlaylistsClicked = onPlaylistsClicked,
                onArtistsClicked = onArtistsClicked,
                onFavoritesClicked = onFavoritesClicked
            )
        }
    }
}
@Composable
fun FirstTopAppBarRow(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    onMenuClicked: () -> Unit,
    dropDownMenu: @Composable () -> Unit
){
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
            dropDownMenu()
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                )
            }
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                )
            }
            Spacer(modifier = modifier.width(7.dp))
        }
    }
}
@Composable
fun SecondTopAppBarRow(
    modifier: Modifier = Modifier,
    currentPage: Int,
    onTracksClicked: () -> String,
    onAlbumsClicked: () -> String,
    onPlaylistsClicked: () -> String,
    onArtistsClicked: () -> String,
    onFavoritesClicked: () -> String
){
    var pageSelected: String by remember { mutableStateOf("Tracks") }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .padding(bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = { pageSelected = onTracksClicked() }) {
            Text(
                text = "Tracks",
                color = if(currentPage == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                fontWeight = if(currentPage == 0) FontWeight.Bold else null,
                fontSize = if(currentPage == 0) 19.sp else TextUnit.Unspecified
            )
        }
        TextButton(onClick = { pageSelected = onAlbumsClicked() }) {
            Text(
                text = "Albums",
                color = if(currentPage == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                fontWeight = if(currentPage == 1) FontWeight.Bold else null,
                fontSize = if(currentPage == 1) 19.sp else TextUnit.Unspecified
            )
        }
        TextButton(onClick = { pageSelected = onPlaylistsClicked() }) {
            Text(
                text = "Playlists",
                color = if(currentPage == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                fontWeight = if(currentPage == 2) FontWeight.Bold else null,
                fontSize = if(currentPage == 2) 19.sp else TextUnit.Unspecified
            )
        }
        TextButton(onClick = { pageSelected = onArtistsClicked() }) {
            Text(
                text = "Artists",
                color = if(currentPage == 3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                fontWeight = if(currentPage == 3) FontWeight.Bold else null,
                fontSize = if(currentPage == 3) 19.sp else TextUnit.Unspecified
            )
        }
        TextButton(onClick = { pageSelected = onFavoritesClicked() }) {
            Text(
                text = "Favorites",
                color = if(currentPage == 4) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                fontWeight = if(currentPage == 4) FontWeight.Bold else null,
                fontSize = if(currentPage == 4) 19.sp else TextUnit.Unspecified,
                maxLines = 1
            )
        }
    }
}