package com.example.musicplayer.ui.screens.getStartedScreen

import android.Manifest
import android.os.Build
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.musicplayer.permissions.PermissionsViewModel
import com.example.musicplayer.ui.navigation.ChooseThemeScreenN
import com.example.musicplayer.ui.navigation.GetStartedScreenN

fun NavGraphBuilder.getStartedNavigation(
    navController: NavHostController,
    permissionsViewModel: PermissionsViewModel
){
    composable<GetStartedScreenN>{
        val permissionsToRequest = if(Build.VERSION.SDK_INT >= 33)
            arrayOf(Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.POST_NOTIFICATIONS)
            else arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        val chooseThemeScreenN = ChooseThemeScreenN(permissionsToRequest)
        GetStartedScreen(
            onAllPermissionsGranted = {
                navController.navigate(chooseThemeScreenN)
            },
            permissionsToRequest = permissionsToRequest,
            permissionsViewModel = permissionsViewModel
        )
    }
}