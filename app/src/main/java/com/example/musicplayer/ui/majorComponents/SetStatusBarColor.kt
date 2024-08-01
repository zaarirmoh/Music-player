package com.example.musicplayer.ui.majorComponents

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

@Composable
fun SetStatusBarColor(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
){
    val view = LocalView.current
    if(!view.isInEditMode){
        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
        }

    }
}