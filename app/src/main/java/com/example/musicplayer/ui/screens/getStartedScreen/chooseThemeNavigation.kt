package com.example.musicplayer.ui.screens.getStartedScreen

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.musicplayer.permissions.PermissionsViewModel
import com.example.musicplayer.ui.navigation.ChooseThemeScreenN
import com.example.musicplayer.ui.navigation.HomeScreenN

fun NavGraphBuilder.chooseThemeNavigation(
    navController: NavController,
    isAppThemeDark: MutableState<Boolean>,
    activity: Activity,
    permissionsViewModel: PermissionsViewModel
){
    composable<ChooseThemeScreenN>{
        val args = it.toRoute<ChooseThemeScreenN>()
        ChooseThemeScreen(
            onContinueButtonClicked = {
                navController.navigate(HomeScreenN)
            },
            isAppThemeDark = isAppThemeDark,
            activity = activity,
            permissionsViewModel = permissionsViewModel,
            permissionsToRequest = args.permissionsToRequest
        )
    }
}