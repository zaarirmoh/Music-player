package com.example.musicplayer.ui.notSureComponent

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

@OptIn(UnstableApi::class)
@Composable
fun PlayerView(exoPlayer: Player) {
    AndroidView(
        factory = { context ->
            androidx.media3.ui.PlayerView(context).apply {
                player = exoPlayer
                useController = true
                artworkDisplayMode = androidx.media3.ui.PlayerView.ARTWORK_DISPLAY_MODE_FIT
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
@Composable
fun rememberExoPlayer(context: Context, audioUri: String): ExoPlayer {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(audioUri)
            setMediaItem(mediaItem)
            prepare()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    return exoPlayer
}
@Composable
fun AudioPlayerControls(exoPlayer: ExoPlayer) {
    val isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
        }) {
            Text(if (isPlaying) "Pause" else "Play")
        }
    }
}
/*
class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null
    private var mediaSessionForToken: android.media.session.MediaSession?  = null//android.media.session.MediaSession(this, "token")
    // Create your player and media session in the onCreate lifecycle event
    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
        val mediaItem = MediaItem.fromUri("/storage/emulated/0/snaptube/download/SnapTube Audio/Soolking feat. Dadju - Meleğim [Clip Officiel] Prod by Nyadjiko(MP3_160K).mp3")
        mediaSessionForToken = android.media.session.MediaSession(this, "token")
        val mediaItem2 = MediaItem.Builder()
            .setMediaId("1000000056")
            .setUri("/storage/emulated/0/Download/snaptube/download/SnapTube Audio/LA CANON 16 Didin klach akhtini أخطيني  2015(MP3_160K).mp3")
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist("Rap dz")
                    .setTitle("LA CANON 16 Didin klach akhtini أخطيني  2015")
                    .setArtworkUri(null)
                    .build()
            )
            .build()
        //val mediaSessionCompat: MediaSessionCompat = MediaSessionCompat.fromMediaSession(this,mediaSession)
        player.setMediaItem(mediaItem2)
        //val mediaSessionCompatToken = mediaSessionCompat.sessionToken
        //val sessionToken = mediaSessionCompatToken as android.media.session.MediaSession.Token
        //val token = mediaSession!!.sessionCompatToken as android.media.session.MediaSession.Token
        val notification = Notification.Builder(this)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle("Track title")
            .setContentText("Artist - Album")
            .setStyle(Notification.MediaStyle().setMediaSession(mediaSessionForToken!!.sessionToken))
            .build()
            //.setLargeIcon(R.drawable.app_icon)
            //.setStyle(Notification.MediaStyle()
                //.setMediaSession(mediaSession!!.token))
            //.build()
        val notification2 = MediaNotification(1,notification)
        player.prepare()
        //val notificationProvider = DefaultMediaNotificationProvider.Builder(this).build()
        //setMediaNotificationProvider(notificationProvider)
        player.play()
        //startForeground(1,notification)
    }
    // The user dismissed the app from the recent tasks
    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player
        if (player!!.playWhenReady) {
            // Make sure the service is not in foreground.
            player.pause()
        }
        stopSelf()
    }

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession
}
 */
//val exoPlayer = rememberExoPlayer(context,"/storage/emulated/0/snaptube/download/SnapTube Audio/Soolking feat. Dadju - Meleğim [Clip Officiel] Prod by Nyadjiko(MP3_160K).mp3")
//PlayerView(exoPlayer = exoPlayer)
//AudioPlayerControls(exoPlayer = exoPlayer)
/*
var mediaController: MediaController? = null
    controllerFuture.addListener({
        mediaController = controllerFuture.get()
    }, MoreExecutors.directExecutor())
    //val player = ExoPlayer.Builder(context).build()

    //mediaController?.let { PlayerView(exoPlayer = it) }
 */