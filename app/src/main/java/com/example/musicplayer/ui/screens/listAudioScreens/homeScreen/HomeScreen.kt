package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.ui.majorComponents.TopAppBar
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreen.tracksScreen.TracksScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
){
    val pagerState = rememberPagerState(pageCount = {
        5
    })
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar()
        }
    ) { innerPadding ->
        Column {
            HorizontalPager(
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


