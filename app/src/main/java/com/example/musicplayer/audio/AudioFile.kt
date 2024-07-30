package com.example.musicplayer.audio

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable



data class AudioFile(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val data: String,
    var albumArt: Bitmap? = null
)
