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
import android.media.MediaPlayer
import android.provider.MediaStore
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableIntStateOf
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.example.musicplayer.MainActivity
import com.example.musicplayer.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * AudioViewModel class to handle audio files , load them from running device
 * get there art
 */
class AudioViewModel: ViewModel() {


    private var mediaPlayer: MediaPlayer? = null

    fun playAudio(filePath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepare()
                start()
            }
        }
    }

    fun stopAudio() {
        viewModelScope.launch(Dispatchers.IO) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    /**
     * Function to associate each audio with its picture
     */
    fun mapAudioWithPicture(
        audioFiles: List<AudioFile>,
        contentResolver: ContentResolver
    ){
        audioFiles.forEach { audioFile ->
            val albumArt = getAlbumArt(audioFile.data, contentResolver, audioFile.id)
            audioFile.albumArt = albumArt
        }
    }
    /**
     * Function to get album art for a given audio file
     */
    private fun getAlbumArt(filePath: String, contentResolver: ContentResolver, albumId: Long): Bitmap? {
        // Try to get embedded album art
        val embeddedArt = getEmbeddedAlbumArt(filePath)
        android.util.Log.d("here",embeddedArt.toString())
        if (embeddedArt != null) return embeddedArt
        android.util.Log.e("here","here")
        // Try to get album art from media store
        return getAlbumArtFromMediaStore(contentResolver, albumId)
    }

    /**
     * Function to load audio files from running device
     * Example: loadAudioFiles(contentResolver)
     */
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

                audioList.add(AudioFile(id, title, artist, album, data))
            }
        }
        return audioList
    }

    @OptIn(UnstableApi::class)
    fun createNotification(
        context: Context,
        mediaSession: MediaSession,
        audioFiles: List<AudioFile>
    ): Notification{
        val audioIndex = mutableIntStateOf(mediaSession.player.currentMediaItemIndex)
        val playIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_PLAY"
        }
        val pauseIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_PAUSE"
        }
        val nextIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_NEXT"
        }
        val previousIntent = Intent(context, PlayBackService::class.java).apply {
            action = "ACTION_PREVIOUS"
        }
        val contentIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // Create PendingIntents for each action
        val playPendingIntent: PendingIntent = PendingIntent.getService(context, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)
        val pausePendingIntent: PendingIntent = PendingIntent.getService(context, 0, pauseIntent, PendingIntent.FLAG_IMMUTABLE)
        val nextPendingIntent: PendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE)
        val previousPendingIntent: PendingIntent = PendingIntent.getService(context, 0, previousIntent, PendingIntent.FLAG_IMMUTABLE)
        val contentPendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context,"media_notification")
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle(audioFiles.get(audioIndex.intValue).artist)
            .setContentText(audioFiles.get(audioIndex.intValue).title)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setLargeIcon(audioFiles.get(audioIndex.intValue).albumArt)
            .setContentIntent(contentPendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionCompatToken)
                    .setShowActionsInCompactView(0,1,2,4)
            )
            .addAction(NotificationCompat.Action(R.drawable.ic_launcher_foreground,"Previous",playPendingIntent))
            .addAction(NotificationCompat.Action(R.drawable.ic_launcher_foreground,"Previous",pausePendingIntent))
            .addAction(NotificationCompat.Action(R.drawable.ic_launcher_foreground,"Previous",nextPendingIntent))
            .addAction(NotificationCompat.Action(R.drawable.ic_launcher_foreground,"Previous",previousPendingIntent))
            .build()
        return notification
    }
    private fun getAlbumArtFromMediaStore(contentResolver: ContentResolver, albumId: Long): Bitmap? {
        val uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.Albums.ALBUM_ART)
        val selection = "${MediaStore.Audio.Albums._ID}=?"
        val selectionArgs = arrayOf(albumId.toString())

        val cursor: Cursor? = contentResolver.query(uri, projection, selection, selectionArgs, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val albumArtColumn = it.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)
                val albumArtPath = it.getString(albumArtColumn)
                if (albumArtPath != null) {
                    return BitmapFactory.decodeFile(albumArtPath)
                }
            }
        }
        return null
    }
    private fun getEmbeddedAlbumArt(filePath: String): Bitmap? {
        val retriever = MediaMetadataRetriever()
        return try {
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
    }
}
