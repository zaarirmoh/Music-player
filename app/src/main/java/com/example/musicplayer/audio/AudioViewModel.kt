package com.example.musicplayer.audio

import android.app.Notification
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.example.musicplayer.MainActivity
import com.example.musicplayer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AudioViewModel: ViewModel() {

    private val _audioFiles = MutableStateFlow(listOf<AudioFile>())
    val audioFiles = _audioFiles.asStateFlow()

    private fun createMediaItem(audioFile: AudioFile): MediaItem {
        return MediaItem
            .Builder()
            .setMediaId(audioFile.id.toString())
            .setUri(audioFile.data)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(audioFile.artist)
                    .setTitle(audioFile.title)
                    .setArtworkUri(null)
                    .build()
            )
            .build()
    }

    fun addMediaItems(player: Player,audioFiles: List<AudioFile>){
        audioFiles.forEach { audioFile ->
            val mediaItem = createMediaItem(audioFile)
            player.addMediaItem(mediaItem)
        }
    }

    private fun getAlbumArt(filePath: String): Bitmap? {
        val retriever = MediaMetadataRetriever()
        val bitmapResult = try {
            retriever.setDataSource(filePath)
            val art = retriever.embeddedPicture
            if (art != null) {
                BitmapFactory.decodeByteArray(art, 0, art.size)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        } finally {
            retriever.release()
        }
        return bitmapResult
    }


    fun loadAudioFiles(contentResolver: ContentResolver): List<AudioFile> {
        val audioList = mutableListOf<AudioFile>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA,
        )
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Audio.Media.TITLE + " ASC"
        )
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val title = it.getString(titleColumn)
                val artist = it.getString(artistColumn)
                val album = it.getString(albumColumn)
                val data = it.getString(dataColumn)
                val albumArt = getAlbumArt(data)
                audioList.add(AudioFile(id, title, artist, album, data, albumArt))
            }
        }
        _audioFiles.value = audioList
        return audioList
    }

    @OptIn(UnstableApi::class)
    fun createNotification(
        context: Context,
        isPlaying: Boolean,
        audioFile: AudioFile,
        mediaSession: MediaSession
    ): Notification {
        /** Play action ---------------------------*/
        val playIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_PLAY"
        }
        val playPendingIntent: PendingIntent = PendingIntent.getService(context, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)
        val playAction = NotificationCompat.Action(R.drawable.play_arrow_48px,"Play",playPendingIntent)

        /** Pause action ---------------------------*/
        val pauseIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_PAUSE"
        }
        val pausePendingIntent: PendingIntent = PendingIntent.getService(context, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE)
        val pauseAction = NotificationCompat.Action(R.drawable.pause_48px,"Pause",pausePendingIntent)

        /** Next action ---------------------------*/
        val nextIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_NEXT"
        }
        val nextPendingIntent: PendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE)
        val nextAction = NotificationCompat.Action(R.drawable.skip_next_48px,"Next",nextPendingIntent)

        /** Previous action ---------------------------*/
        val previousIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_PREVIOUS"
        }
        val previousPendingIntent: PendingIntent = PendingIntent.getService(context, 0, previousIntent, PendingIntent.FLAG_IMMUTABLE)
        val previousAction = NotificationCompat.Action(R.drawable.skip_previous_48px,"Previous",previousPendingIntent)

        /** Close action ---------------------------*/
        val closeIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_CLOSE"
        }
        val closePendingIntent: PendingIntent = PendingIntent.getService(context, 0, closeIntent, PendingIntent.FLAG_IMMUTABLE)
        val closeAction = NotificationCompat.Action(R.drawable.close_48,"Close",closePendingIntent)

        /** Content action ---------------------------*/
        val contentIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val contentPendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context,"media_notification")
            .setSmallIcon(R.drawable.music_note_48px)
            .setContentTitle(audioFile.artist)
            .setContentText(audioFile.title)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLargeIcon(audioFile.albumArt)
            .setContentIntent(contentPendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionCompatToken)
                    .setShowActionsInCompactView(0,1,2,4)
            )
            .addAction(previousAction)
            .addAction(if(isPlaying) pauseAction else playAction)
            .addAction(nextAction)
            .addAction(closeAction)
            .setOnlyAlertOnce(true)
            .build()
        return notification
    }
}