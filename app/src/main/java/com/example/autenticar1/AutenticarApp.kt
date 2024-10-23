package com.example.autenticar1

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.autenticar1.common.composable.PermissionDialog
import com.example.autenticar1.common.composable.RationaleDialog
import com.example.autenticar1.ui.theme.Autenticar1Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun AutenticarApp(){
    Autenticar1Theme{
        RequestNotificationPermissionDialog()

        Surface(color = MaterialTheme.colorScheme.background) {

        }

    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }

}

