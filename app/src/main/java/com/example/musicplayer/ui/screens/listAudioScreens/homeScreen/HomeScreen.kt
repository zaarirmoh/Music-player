package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.ui.majorComponents.TopAppBar
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.albumsScreen.AlbumsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.artistsScreen.ArtistsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.favoritesScreen.FavoritesScreen
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.playlistsScreen.PlaylistsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.tracksScreen.TracksScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    intent: Intent,
    onTracksClicked: () -> String,
    onAlbumsClicked: () -> String,
    onPlaylistsClicked: () -> String,
    onArtistsClicked: () -> String,
    onFavoritesClicked: () -> String,

    ){

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                onTracksClicked = onTracksClicked,
                onAlbumsClicked = onAlbumsClicked,
                onPlaylistsClicked = onPlaylistsClicked,
                onArtistsClicked = onArtistsClicked,
                onFavoritesClicked = onFavoritesClicked
            )
        }
    ) { innerPadding ->
        Column {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> TracksScreen(
                        paddingValues = innerPadding,
                        intent = intent
                    )
                    1 -> AlbumsScreen(paddingValues = innerPadding)
                    2 -> PlaylistsScreen()
                    3 -> ArtistsScreen()
                    4 -> FavoritesScreen()
                }
            }
        }
    }
}


