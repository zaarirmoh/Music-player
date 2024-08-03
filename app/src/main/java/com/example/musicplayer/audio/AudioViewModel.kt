package com.example.musicplayer.audio

import android.content.ContentResolver
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.core.graphics.set
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import java.io.IOException

class AudioViewModel: ViewModel() {

    fun playAudio(player: Player){
        player.prepare()
        player.play()
    }

    fun playAudio(player: Player,audioIndex: Int){
        player.seekTo(audioIndex,0)
        player.prepare()
        player.play()
    }

    fun pauseAudio(player: Player){
        player.pause()
    }

    fun releasePlayer(player: Player){
        player.stop()
        player.release()
    }

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
        return audioList
    }

}