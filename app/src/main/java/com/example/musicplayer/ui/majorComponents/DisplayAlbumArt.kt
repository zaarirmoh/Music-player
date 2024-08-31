package com.example.musicplayer.ui.majorComponents

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.AlbumArtShapes

@Composable
fun DisplayAlbumArt(
    modifier: Modifier = Modifier,
    bitmap: Bitmap?,
    size: Dp = 52.dp,
    shape: Shape = AlbumArtShapes.extraSmall,
    onAlbumArtClicked: () -> Unit = {},
){
    Card(
        onClick = onAlbumArtClicked,
        modifier = modifier.size(size),
        shape = shape,
    ) {
        if (bitmap != null) {
            val newWidth = calculateArtWorkWidth(bitmap,size)
            val newHeight = calculateArtWorkHeight(bitmap,size)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
            Image(
                bitmap = scaledBitmap.asImageBitmap(),
                contentDescription = "Album Art",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        } else {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.MusicNote,
                    contentDescription = null
                )
            }
        }

    }
}
fun calculateArtWorkWidth(bitmap: Bitmap,size: Dp): Int{
    val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    return if (bitmap.width > bitmap.height) {
        size.value.toInt() + 50
    } else {
        (size.value.toInt() + 50 * aspectRatio).toInt()
    }
}
fun calculateArtWorkHeight(bitmap: Bitmap,size: Dp): Int{
    val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    return if (bitmap.width > bitmap.height) {
        (size.value.toInt() + 50 / aspectRatio).toInt()
    } else {
        size.value.toInt() + 50
    }
}