package com.example.musicplayer.ui.screens.listAudioScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.audio.AudioViewModel
import com.example.musicplayer.ui.majorComponents.AudioList
import com.example.musicplayer.ui.theme.chopsicFontFamily
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar()
        }
    ) { innerPadding ->
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        Column {
            HorizontalPager(
                count = 2, // Number of pages
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> TracksScreen(paddingValues = innerPadding)
                    1 -> PlaylistsScreen()
                }
            }
        }


    }
}
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun MusicPlayerPager() {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        HorizontalPager(
            count = 3, // Number of pages
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> HomeScreen()
                1 -> PlaylistsScreen()
                2 -> SettingsScreen()
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            }) {
                Text("Home")
            }
            Button(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }) {
                Text("Playlists")
            }
            Button(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(2)
                }
            }) {
                Text("Settings")
            }
        }
    }
}


@Composable
fun PlaylistsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Playlists Screen")
    }
}

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Settings Screen")
    }
}
@Composable
fun GetRidOfInnerPadding(
    paddingValues: PaddingValues
){}
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