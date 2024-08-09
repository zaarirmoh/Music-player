package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import com.example.musicplayer.audio.AudioViewModel
import com.example.musicplayer.ui.majorComponents.TopAppBar
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.albumsScreen.AlbumsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.artistsScreen.ArtistsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.favoritesScreen.FavoritesScreen
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.playlistsScreen.PlaylistsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.pagerScreens.tracksScreen.TracksScreen
import com.google.common.util.concurrent.ListenableFuture

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    audioViewModel: AudioViewModel,
    intent: Intent,
    context: Context,
    controllerFuture: ListenableFuture<MediaController>,
    expendedMenu: MutableState<Boolean>,
    onSearchClicked: () -> Unit = {},
    onMenuIconClicked: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    onAboutOurApplicationClicked: () -> Unit = {},
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
                currentPage = pagerState.currentPage,
                onMenuClicked = onMenuIconClicked,
                onSearchClicked = onSearchClicked,
                onTracksClicked = onTracksClicked,
                onAlbumsClicked = onAlbumsClicked,
                onPlaylistsClicked = onPlaylistsClicked,
                onArtistsClicked = onArtistsClicked,
                onFavoritesClicked = onFavoritesClicked,
                dropDownMenu = {
                    DropDownMenu(
                        expanded = expendedMenu,
                        onEditClicked = onEditClicked,
                        onSettingsClicked = onSettingsClicked,
                        onAboutOurApplicationClicked = onAboutOurApplicationClicked
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> TracksScreen(
                        audioViewModel = audioViewModel,
                        intent = intent,
                        context = context,
                        controllerFuture = controllerFuture
                    )
                    1 -> AlbumsScreen()
                    2 -> PlaylistsScreen()
                    3 -> ArtistsScreen()
                    4 -> FavoritesScreen()
                }
            }
        }
    }
}


