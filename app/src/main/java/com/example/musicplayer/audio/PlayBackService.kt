package com.example.musicplayer.audio

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.musicplayer.MainActivity
import com.example.musicplayer.R
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import okhttp3.internal.notify

class PlayBackService: MediaSessionService() {

    private var mediaSession: MediaSession? = null
    private var player: Player? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("here","entered play back service onCreate")
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player!!).build()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d("here","entered play back service onStartCommand")
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var allTrackList: List<AudioFile> = listOf()
        var mediaItem: MediaItem
        val audioFilesJson = intent?.getStringExtra(AUDIO_FILES)
        if (audioFilesJson != null) {
            val type = object : TypeToken<List<AudioFile>>() {}.type
            allTrackList = Gson().fromJson(audioFilesJson, type)
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
                player!!.addMediaItem(mediaItem)
            }
            val currentMediaItemIndex = intent.getIntExtra(AUDIO_INDEX, 0)
            val notification = createNotification(allTrackList)
            player!!.seekTo(currentMediaItemIndex, 0)
            player!!.prepare()
            player!!.play()
            startForeground(1,notification)
        }
        when (intent?.action) {
            "ACTION_PLAY" -> {
                player?.prepare()
                player?.play()
                val notification = createNotification(allTrackList)
                notificationManager.notify(1,notification)
            }
            "ACTION_PAUSE" -> {
                player?.pause()
                val notification = createNotification(allTrackList)
                notificationManager.notify(1,notification)
            }
            "ACTION_NEXT" -> {
                player?.seekToNext()
                val notification = createNotification(allTrackList)
                notificationManager.notify(1,notification)
            }
            "ACTION_PREVIOUS" -> {
                player?.seekToPrevious()
                val notification = createNotification(allTrackList)
                notificationManager.notify(1,notification)
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

    @OptIn(UnstableApi::class)
    fun createNotification(
        audioFiles: List<AudioFile>
    ): Notification {
        val audioIndex = mutableIntStateOf(mediaSession!!.player.currentMediaItemIndex)
        val isPlaying = mutableStateOf(mediaSession!!.player.isPlaying)
        val playIntent = Intent(this, PlayBackService::class.java).apply {
            action = "ACTION_PLAY"
        }
        val pauseIntent = Intent(this, PlayBackService::class.java).apply {
            action = "ACTION_PAUSE"
        }
        val nextIntent = Intent(this, PlayBackService::class.java).apply {
            action = "ACTION_NEXT"
        }
        val previousIntent = Intent(this, PlayBackService::class.java).apply {
            action = "ACTION_PREVIOUS"
        }
        val closeIntent = Intent(this, PlayBackService::class.java).apply {
            action = "ACTION_CLOSE"
        }
        val contentIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // Create PendingIntents for each action
        val playPendingIntent: PendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)
        val pausePendingIntent: PendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE)
        val nextPendingIntent: PendingIntent = PendingIntent.getService(this, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE)
        val previousPendingIntent: PendingIntent = PendingIntent.getService(this, 0, previousIntent, PendingIntent.FLAG_IMMUTABLE)
        val closePendingIntent: PendingIntent = PendingIntent.getService(this, 0, closeIntent, PendingIntent.FLAG_IMMUTABLE)
        val contentPendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this,"media_notification")
            .setSmallIcon(R.drawable.music_note_48px)
            .setContentTitle(audioFiles[audioIndex.intValue].artist)
            .setContentText(audioFiles[audioIndex.intValue].title)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLargeIcon(audioFiles[audioIndex.intValue].albumArt)
            .setContentIntent(contentPendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession!!.sessionCompatToken)
                    .setShowActionsInCompactView(0,1,2,4)
            )
            .addAction(NotificationCompat.Action(R.drawable.skip_previous_48px,"Previous",previousPendingIntent))
            .addAction(
                if(isPlaying.value){
                    NotificationCompat.Action(R.drawable.pause_48px,"Pause",pausePendingIntent)
                }else{
                    NotificationCompat.Action(R.drawable.play_arrow_48px,"Play",playPendingIntent)
                }
            )
            .addAction(NotificationCompat.Action(R.drawable.skip_next_48px,"Next",nextPendingIntent))
            .addAction(NotificationCompat.Action(R.drawable.close_48,"Close",closePendingIntent))
            .build()
        return notification
    }
    companion object {
        const val AUDIO_FILES = "extra_audio_files"
        const val AUDIO_INDEX = "extra_audio_index"
    }
}