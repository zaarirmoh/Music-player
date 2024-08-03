package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen

import android.content.ComponentName
import android.content.Intent
import android.icu.text.SymbolTable
import android.util.Log
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.musicplayer.audio.PlayBackService
import com.example.musicplayer.audio.PlayBackServiceOld
import com.example.musicplayer.ui.navigation.HomeScreenN
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeNavigation(){
    composable<HomeScreenN> {
        val pagerState = rememberPagerState(pageCount = {
            5
        })
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        val intent = Intent(context,PlayBackService::class.java)
        val sessionToken =
            SessionToken(context, ComponentName(context, PlayBackService::class.java))
        val mediaController = MediaController.Builder(context, sessionToken).buildAsync()
        mediaController.addListener({
            mediaController.get()
        }, MoreExecutors.directExecutor())
        val intent2 = Intent(context, PlayBackServiceOld::class.java)
        HomeScreen(
            pagerState = pagerState,
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
            intent = intent
        )
    }
}