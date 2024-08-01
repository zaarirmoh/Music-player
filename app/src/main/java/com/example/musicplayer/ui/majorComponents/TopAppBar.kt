package com.example.musicplayer.ui.majorComponents

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.ui.theme.chopsicFontFamily

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
){
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
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        modifier = modifier.clickable {  }
                    )
                    Spacer(modifier = modifier.width(7.dp))
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null,
                        modifier = modifier.clickable {  }
                    )
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
                Text(
                    text = "Tracks",
                    modifier = modifier.clickable {  }
                )
                Text(
                    text = "Albums",
                    modifier = modifier.clickable {  }
                )
                Text(
                    text = "Playlists",
                    modifier = modifier.clickable {  }
                )
                Text(
                    text = "Artists",
                    modifier = modifier.clickable {  }
                )
                Text(
                    text = "Favorites",
                    modifier = modifier.clickable {  }
                )
                Icon(
                    imageVector = Icons.Filled.Shuffle,
                    contentDescription = null,
                    modifier = modifier.clickable {  }
                )
            }
        }
    }
}