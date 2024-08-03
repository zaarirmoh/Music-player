package com.example.musicplayer.audio

import android.content.Intent
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService


class PlayBackServiceOld: MediaSessionService() {
    private var mediaSession: MediaSession? = null
    private val audioViewModelOld = AudioViewModelOld()
    override fun onCreate() {
        super.onCreate()
        Log.d("here","entered play back service old")
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
        val allTrackList = audioViewModelOld.loadAudioFiles(contentResolver)
        audioViewModelOld.mapAudioWithPicture(allTrackList, contentResolver)
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
        val notification = audioViewModelOld.createNotification(this, mediaSession!!, allTrackList)
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
