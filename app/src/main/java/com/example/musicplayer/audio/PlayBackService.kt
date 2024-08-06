package com.example.musicplayer.audio

import android.content.Intent
import android.util.Log
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.musicplayer.NOTIFICATION_ID
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class PlayBackService: MediaSessionService() {

    private var mediaSession: MediaSession? = null
    private var player: Player? = null
    private val audioViewModel = AudioViewModel()
    private var audioFiles: List<AudioFile> = listOf()

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player!!).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val audioFilesJson = intent?.getStringExtra(AUDIO_FILES)
        if (audioFilesJson != null) {
            Log.d("here",audioFilesJson.toString())
            val type = object : TypeToken<List<AudioFile>>() {}.type
            audioFiles = Gson().fromJson(audioFilesJson, type)
            audioViewModel.addMediaItems(player!!,audioFiles)
            player!!.prepare()
        }
        when (intent?.action) {
            "ACTION_PLAY" -> {
                player?.prepare()
                player?.play()
                val notification = audioViewModel.createNotification(
                    context = this,
                    isPlaying = player!!.isPlaying,
                    audioFile = audioFiles[player!!.currentMediaItemIndex],
                    mediaSession = mediaSession!!
                )
                //notificationManager.notify(NOTIFICATION_ID,notification)
                startForeground(NOTIFICATION_ID,notification)
            }
            "ACTION_PAUSE" -> {
                player?.pause()
                val notification = audioViewModel.createNotification(
                    context = this,
                    isPlaying = player!!.isPlaying,
                    audioFile = audioFiles[player!!.currentMediaItemIndex],
                    mediaSession = mediaSession!!
                )
                //notificationManager.notify(NOTIFICATION_ID,notification)
                startForeground(NOTIFICATION_ID,notification)
            }
            "ACTION_NEXT" -> {
                player?.seekToNext()
                val notification = audioViewModel.createNotification(
                    context = this,
                    isPlaying = player!!.isPlaying,
                    audioFile = audioFiles[player!!.currentMediaItemIndex],
                    mediaSession = mediaSession!!
                )
                //notificationManager.notify(NOTIFICATION_ID,notification)
                startForeground(NOTIFICATION_ID,notification)
            }
            "ACTION_PREVIOUS" -> {
                player?.seekToPrevious()
                val notification = audioViewModel.createNotification(
                    context = this,
                    isPlaying = player!!.isPlaying,
                    audioFile = audioFiles[player!!.currentMediaItemIndex],
                    mediaSession = mediaSession!!)
                //notificationManager.notify(NOTIFICATION_ID,notification)
                startForeground(NOTIFICATION_ID,notification)
            }
            "ACTION_CLOSE" -> {
                player?.release()
                stopSelf()
            }
        }
        //return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (player?.playWhenReady == true) {
            // Make sure the service is not in foreground.
            player?.pause()
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
    companion object {
        const val AUDIO_FILES = "extra_audio_files"
    }
}