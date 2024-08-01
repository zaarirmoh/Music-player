package com.example.musicplayer.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.permissions.PermissionsViewModel
import com.example.musicplayer.ui.screens.getStartedScreen.chooseThemeNavigation
import com.example.musicplayer.ui.screens.getStartedScreen.getStartedNavigation
import com.example.musicplayer.ui.screens.listAudioScreens.homeScreen.HomeScreenDeprecated
import kotlin.reflect.KClass

@Composable
fun <T : Any> MusicPlayerNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: KClass<T>,
    activity: Activity,
    isAppThemeDark: MutableState<Boolean>
){
    val permissionsViewModel: PermissionsViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        getStartedNavigation(
            navController = navController,
            permissionsViewModel = permissionsViewModel
        )

        chooseThemeNavigation(
            navController = navController,
            isAppThemeDark = isAppThemeDark,
            activity = activity,
            permissionsViewModel = permissionsViewModel
        )

        composable<HomeScreenN>{
            HomeScreenDeprecated()
        }

    }
}