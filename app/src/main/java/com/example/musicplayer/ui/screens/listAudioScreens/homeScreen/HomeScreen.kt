package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import com.example.musicplayer.audio.AudioFile
import com.example.musicplayer.audio.AudioViewModel
import com.example.musicplayer.ui.majorComponents.AudioController
import com.example.musicplayer.ui.majorComponents.TopAppBar
import com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.albumsScreen.AlbumsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.artistsScreen.ArtistsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.favoritesScreen.FavoritesScreen
import com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.playlistsScreen.PlaylistsScreen
import com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.pagerScreens.tracksScreen.TracksScreen
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    audioViewModel: AudioViewModel,
    intent: Intent,
    context: Context,
    controllerFuture: ListenableFuture<MediaController>,
    expandedMenu: MutableState<Boolean>,
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
    var audioFile: AudioFile by remember {
        mutableStateOf(AudioFile(0L,"mohamed","zaarir","rayan","1CS",null))
    }
    val isPlaying = remember { mutableStateOf(false) }
    val isServiceStarted = remember { mutableStateOf(false) }
    val collectNewAudioFile = remember { mutableStateOf(false) }
    val currentAudioSelected = remember { mutableIntStateOf(0) }
    LaunchedEffect(key1 = collectNewAudioFile.value) {
        controllerFuture.addListener({
            val mediaController = controllerFuture.get()
            audioFile = audioViewModel.audioFiles.value[mediaController.currentMediaItemIndex]
        }, MoreExecutors.directExecutor())

    }
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
                        expanded = expandedMenu,
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
            Box(modifier = modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                ) { page ->
                    when (page) {
                        0 -> TracksScreen(
                            audioViewModel = audioViewModel,
                            intent = intent,
                            context = context,
                            controllerFuture = controllerFuture,
                            isPlaying = isPlaying,
                            isServiceStarted = isServiceStarted,
                            collectNewAudioFile = collectNewAudioFile,
                            currentAudioSelected = currentAudioSelected,
                        )
                        1 -> AlbumsScreen(audioViewModel = audioViewModel)
                        2 -> PlaylistsScreen()
                        3 -> ArtistsScreen()
                        4 -> FavoritesScreen()
                    }
                }
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    AudioController(
                        audioFile = audioFile,
                        isPlaying = isPlaying.value,
                        onPreviousClicked = {
                            if(!isServiceStarted.value){
                                context.startService(intent)
                                isServiceStarted.value = true
                            }
                            controllerFuture.addListener({
                                val mediaController = controllerFuture.get()
                                mediaController.seekToPrevious()
                                mediaController.prepare()
                                mediaController.play()
                                isPlaying.value = true
                                if(currentAudioSelected.intValue > 0) currentAudioSelected.intValue--
                                collectNewAudioFile.value = !collectNewAudioFile.value
                            }, MoreExecutors.directExecutor())
                        },
                        onPlayPauseClicked = {
                            if(!isServiceStarted.value){
                                context.startService(intent)
                                isServiceStarted.value = true
                            }
                            controllerFuture.addListener({
                                val mediaController = controllerFuture.get()
                                if(mediaController.isPlaying){
                                    mediaController.pause()
                                    isPlaying.value = false
                                }else{
                                    mediaController.prepare()
                                    mediaController.play()
                                    isPlaying.value = true
                                }
                            }, MoreExecutors.directExecutor())
                        },
                        onNextClicked = {
                            if(!isServiceStarted.value){
                                context.startService(intent)
                                isServiceStarted.value = true
                            }
                            controllerFuture.addListener({
                                val mediaController = controllerFuture.get()
                                mediaController.seekToNext()
                                mediaController.prepare()
                                mediaController.play()
                                isPlaying.value = true
                                if(currentAudioSelected.intValue < audioViewModel.audioFiles.value.size-1) currentAudioSelected.intValue++
                                collectNewAudioFile.value = !collectNewAudioFile.value
                            }, MoreExecutors.directExecutor())
                        },
                        onShuffleClicked = {}
                    )
                }
            }
        }
    }
}