package com.example.musicplayer.ui.screens.listAudioScreens.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ReadMore
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ReadMore
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.musicplayer.ui.theme.Shapes

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    onEditClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    onAboutOurApplicationClicked: () -> Unit = {}
){
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        offset = DpOffset((-15).dp,(-10).dp)
    ) {
        Card(
            shape = Shapes.large,
            modifier = modifier
                .width(280.dp)
                .height(165.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { onEditClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
                Spacer(modifier = modifier.width(10.dp))
                Text(text = "Edit")
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clickable { onSettingsClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Icon(imageVector = Icons.Rounded.Settings, contentDescription = null)
                Spacer(modifier = modifier.width(10.dp))
                Text(text = "Settings")
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable { onAboutOurApplicationClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(15.dp))
                Icon(imageVector = Icons.AutoMirrored.Rounded.ReadMore, contentDescription = null)
                Spacer(modifier = modifier.width(10.dp))
                Text(text = "About our application")
            }
        }
    }
}