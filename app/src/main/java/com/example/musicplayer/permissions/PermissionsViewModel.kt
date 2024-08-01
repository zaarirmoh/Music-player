package com.example.musicplayer.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class PermissionsViewModel: ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val areAllPermissionsGranted = mutableStateOf(false)

    fun checkAllPermissionsAreGranted(context: Context) {
        while(!areAllPermissionsGranted.value){
            Log.d("here","here")
            areAllPermissionsGranted.value = if(Build.VERSION.SDK_INT >= 33){
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            }else{
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            }
            Log.d("here",areAllPermissionsGranted.value.toString())
        }
    }

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}