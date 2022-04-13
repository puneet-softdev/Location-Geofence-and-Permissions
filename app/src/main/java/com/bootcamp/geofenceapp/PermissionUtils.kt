package com.bootcamp.geofenceapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.N)

fun requestLocationPermissionsByUtils(context: Context, onPermissionsResult:(permissionsArray: Array<String>) -> Unit){
    when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED-> {
            Toast.makeText(context, "Fine Location Permission Already Granted", Toast.LENGTH_LONG).show()
        }

        (context as Activity).shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) || (context as Activity).shouldShowRequestPermissionRationale(
            Manifest.permission.ACCESS_COARSE_LOCATION) -> {
            /* In an educational UI, explain to the user why your app requires this
            permission for a specific feature to behave as expected. In this UI,
            include a "cancel" or "no thanks" button that allows the user to
            continue using your app without granting the permission.
             */
            Toast.makeText(context, "This Location Permission is required to give personalized experience", Toast.LENGTH_LONG).show()
            // show custom dialog explaining why do we need permission,
            // and then on Click of "OK" ask for the permission once again
            onPermissionsResult(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }

        else -> {
            onPermissionsResult(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }
}