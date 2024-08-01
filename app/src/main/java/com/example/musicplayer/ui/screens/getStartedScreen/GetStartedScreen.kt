package com.example.musicplayer.ui.screens.getStartedScreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.R
import com.example.musicplayer.permissions.PermissionsViewModel
import com.example.musicplayer.ui.theme.balooFontFamily
import com.example.musicplayer.ui.theme.chopsicFontFamily

@Composable
fun GetStartedScreen(
    modifier: Modifier = Modifier,
    onAllPermissionsGranted: () -> Unit = {},
    permissionsToRequest: Array<String>,
    permissionsViewModel: PermissionsViewModel,
){
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
            painter = painterResource(id = R.drawable.getstartedfullphoto),
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        //color = Color.White
                    )) {
                        append("From the ")
                    }
                    withStyle(style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                        //color = Color.Red
                    )) {
                        append("latest")
                    }
                    withStyle(style = SpanStyle(
                        //color = Color.White
                    )) {
                        append(" to the ")
                    }
                    withStyle(style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                        //color = Color.Red
                    )) {
                        append("greatest")
                    }
                    withStyle(style = SpanStyle(
                        //color = Color.White
                    )) {
                        append(" hits, play your favorite tracks on \n")
                    }
                    withStyle(style = SpanStyle(
                        //color = Color.Red
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = chopsicFontFamily
                    )) {
                        append("MUSIC PLAYER\n")
                    }
                    withStyle(style = SpanStyle(
                        //color = Color.White
                    )) {
                        append(" Now!")
                    }
                },
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = balooFontFamily,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp, end = 60.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(20.dp))
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, bottom = 100.dp)
                    .height(92.dp),
                onClick = {
                    multiplePermissionResultLauncher.launch(permissionsToRequest)
                    onAllPermissionsGranted()},
                shape = RoundedCornerShape(30.dp),
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = balooFontFamily
                )
            }
        }
    }
}
fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}