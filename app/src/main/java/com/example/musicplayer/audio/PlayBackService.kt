package com.example.musicplayer.audio

import android.content.Intent
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class PlayBackService: MediaSessionService() {
    private var mediaSession: MediaSession? = null
    private val audioViewModel = AudioViewModel()
    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
        val allTrackList = audioViewModel.loadAudioFiles(contentResolver)
        audioViewModel.mapAudioWithPicture(allTrackList, contentResolver)
        var mediaItem: MediaItem
        allTrackList.forEach {
            mediaItem = MediaItem
                .Builder()
                .setMediaId(it.id.toString())
                .setUri(it.data)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setArtist(it.artist)
                        .setTitle(it.title)
                        .setArtworkUri(null)
                        .build()
                )
                .build()
            player.addMediaItem(mediaItem)
        }
        val notification = audioViewModel.createNotification(this, mediaSession!!, allTrackList)
        player.prepare()
        startForeground(1,notification)
        player.play()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession!!.player
        if (player.playWhenReady) {
            // Make sure the service is not in foreground.
            player.pause()
        }
        stopSelf()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}