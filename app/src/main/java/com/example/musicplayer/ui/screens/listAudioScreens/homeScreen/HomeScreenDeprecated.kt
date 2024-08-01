package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.majorComponents.TopAppBar
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreen.tracksScreen.TracksScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun HomeScreenDeprecated(
    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar()
        }
    ) { innerPadding ->
        val pagerState = rememberPagerState()

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

@OptIn(ExperimentalPagerApi::class)
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
                0 -> HomeScreenDeprecated()
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