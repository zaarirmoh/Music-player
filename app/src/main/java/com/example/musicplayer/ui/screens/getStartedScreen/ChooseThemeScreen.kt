package com.example.musicplayer.ui.screens.getStartedScreen

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.example.musicplayer.R
import com.example.musicplayer.permissions.AudioPermissionTextProvider
import com.example.musicplayer.permissions.ExternalStoragePermissionTextProvider
import com.example.musicplayer.permissions.NotificationPermissionTextProvider
import com.example.musicplayer.permissions.PermissionDialog
import com.example.musicplayer.permissions.PermissionsViewModel
import com.example.musicplayer.ui.theme.balooFontFamily

@Composable
fun ChooseThemeScreen(
    modifier: Modifier = Modifier,
    onContinueButtonClicked: () -> Unit = {},
    isAppThemeDark: MutableState<Boolean>,
    permissionsViewModel: PermissionsViewModel,
    activity: Activity,
    permissionsToRequest: Array<String>
){
    val dialogQueue = permissionsViewModel.visiblePermissionDialogQueue
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                permissionsViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.choosethemephoto),
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        ChooseTheme(
            onContinueButtonClicked = onContinueButtonClicked,
            isAppThemeDark = isAppThemeDark
        )
    }

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.READ_MEDIA_AUDIO -> {
                        AudioPermissionTextProvider()
                    }
                    Manifest.permission.POST_NOTIFICATIONS -> {
                        NotificationPermissionTextProvider()
                    }
                    Manifest.permission.READ_EXTERNAL_STORAGE -> {
                        ExternalStoragePermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(activity,permission),
                onDismiss = permissionsViewModel::dismissDialog,
                onOkClick = {
                    permissionsViewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = { activity.openAppSettings() }
            )
        }
}

@Preview
@Composable
fun ChooseTheme(
    modifier: Modifier = Modifier,
    onContinueButtonClicked: () -> Unit = {},
    isAppThemeDark: MutableState<Boolean> = mutableStateOf(true)
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "Choose theme",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = balooFontFamily,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = modifier.height(40.dp))
        ThemeButtons(
            themeChosen = isAppThemeDark,
        )
        Spacer(modifier = modifier.height(70.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 100.dp)
                .height(92.dp),
            onClick = onContinueButtonClicked,
            shape = RoundedCornerShape(30.dp),
        ) {
            Text(
                text = "Continue",
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = balooFontFamily
            )
        }
    }
}

@Preview
@Composable
fun ThemeButtons(
    modifier: Modifier = Modifier,
    themeChosen: MutableState<Boolean> = mutableStateOf(true)
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DarkThemeButton(themeChosen = themeChosen)
        LightThemeButton(themeChosen = themeChosen)
    }
}

@Preview
@Composable
fun DarkThemeButton(
    modifier: Modifier = Modifier,
    themeChosen: MutableState<Boolean> = mutableStateOf(true)
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center, // Center the content
            modifier = Modifier
                .size(73.dp)
                .clickable { (if(!themeChosen.value) themeChosen.value = !themeChosen.value) }
        ) {
            Card(
                modifier = Modifier
                    .size(73.dp)
                    .graphicsLayer {
                        alpha = 0.7f // Set the desired opacity here
                    },
                shape = RoundedCornerShape(50.dp), // Adjust to make it circular
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                border = if(themeChosen.value) BorderStroke(3.dp, MaterialTheme.colorScheme.primary) else null
            ) {
            }
            Icon(
                imageVector = Icons.Outlined.Nightlight,
                contentDescription = null,
                modifier = modifier.size(40.dp),
                tint = if (!themeChosen.value) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = "Dark theme",
            fontSize = 17.sp,
            fontFamily = balooFontFamily,
            color = if(themeChosen.value) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
    }

}

@Preview
@Composable
fun LightThemeButton(
    modifier: Modifier = Modifier,
    themeChosen: MutableState<Boolean> = mutableStateOf(true)
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center, // Center the content
            modifier = Modifier
                .size(73.dp)
                .clickable { if(themeChosen.value) themeChosen.value = !themeChosen.value }
        ) {
            Card(
                modifier = Modifier
                    .size(73.dp)
                    .graphicsLayer {
                        alpha = 0.7f // Set the desired opacity here
                    },
                shape = RoundedCornerShape(50.dp), // Adjust to make it circular
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                border = if(!themeChosen.value) BorderStroke(3.dp, MaterialTheme.colorScheme.primary) else null
            ) {

            }
            Icon(
                imageVector = Icons.Outlined.LightMode,
                contentDescription = null,
                modifier = modifier.size(40.dp),
                tint = if (themeChosen.value) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = "Light theme",
            fontSize = 17.sp,
            fontFamily = balooFontFamily,
            color = if(!themeChosen.value) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
    }
}