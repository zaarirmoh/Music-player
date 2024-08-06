package com.example.musicplayer.audio

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable


@Immutable
data class AudioFile(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val data: String,
    val albumArt: Bitmap?
)
