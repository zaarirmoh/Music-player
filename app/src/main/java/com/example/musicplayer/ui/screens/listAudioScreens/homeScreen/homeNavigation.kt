package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen

import android.content.ComponentName
import android.content.Intent
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.musicplayer.audio.AudioViewModel
import com.example.musicplayer.audio.PlayBackService
import com.example.musicplayer.ui.navigation.HomeScreenN
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeNavigation(){
    composable<HomeScreenN> {
        val audioViewModel: AudioViewModel = viewModel()
        val audioFiles by audioViewModel.audioFiles.collectAsState()
        val pagerState = rememberPagerState(pageCount = {
            5
        })
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        val intent = Intent(context,PlayBackService::class.java)
        val sessionToken =
            SessionToken(context, ComponentName(context, PlayBackService::class.java))
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        audioViewModel.loadAudioFiles(context.contentResolver)
        HomeScreen(
            pagerState = pagerState,
            audioViewModel = audioViewModel,
            intent = intent,
            context = context,
            controllerFuture = controllerFuture,
            onTracksClicked = {
                coroutineScope.launch { pagerState.animateScrollToPage(0) }
                "Tracks"},
            onAlbumsClicked = {
                coroutineScope.launch { pagerState.animateScrollToPage(1) }
                "Albums"},
            onPlaylistsClicked = {
                coroutineScope.launch { pagerState.animateScrollToPage(2) }
                "Playlists"},
            onArtistsClicked = {
                coroutineScope.launch { pagerState.animateScrollToPage(3) }
                "Artists"},
            onFavoritesClicked = {
                coroutineScope.launch { pagerState.animateScrollToPage(4) }
                "Favorites"},
        )
    }
}