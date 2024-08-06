package com.example.musicplayer.ui.navigation

import android.content.Context
import com.example.musicplayer.permissions.PermissionsViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
object GetStartedScreenN

@Serializable
data class ChooseThemeScreenN(
    val permissionsToRequest: Array<String>,
)

@Serializable
object HomeScreenN

data class PagerScreen(
    val name: String,
    val context: Context,
)