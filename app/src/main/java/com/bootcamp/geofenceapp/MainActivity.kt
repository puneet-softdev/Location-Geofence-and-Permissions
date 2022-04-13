package com.bootcamp.geofenceapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bootcamp.geofenceapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    private val listOfPermissions = arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        activityMainBinding.btnShareLocation.setOnClickListener {
            requestPermissionByUsingWhen()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                Toast.makeText(this, "Precise Location Permission Granted", Toast.LENGTH_LONG).show()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                Toast.makeText(this, "Approximate Location Permission Granted", Toast.LENGTH_LONG).show()
            }
            // as per some usecase we might need to work if either of the permission is granted
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) || permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) ->{

            }
            else -> {
                // No location access granted.
                Toast.makeText(this, "Location Permission Not Granted", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val locationResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Location Permission Not Granted", Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestPermissionOneByOne(){
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Fine Location Permission Already Granted", Toast.LENGTH_LONG).show()
        }else if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "This Fine Location Permission is required to give personalized experience", Toast.LENGTH_LONG).show()
            // show custom dialog explaining why do we need permission,
            // and then on Click of "OK" ask for the permission once again
            locationResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            locationResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Coarse Location Permission Already Granted", Toast.LENGTH_LONG).show()
        } else if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            /* In an educational UI, explain to the user why your app requires this
            permission for a specific feature to behave as expected. In this UI,
            include a "cancel" or "no thanks" button that allows the user to
            continue using your app without granting the permission.
             */
            Toast.makeText(this, "This Coarse Location Permission is required to give personalized experience", Toast.LENGTH_LONG).show()
            // show custom dialog explaining why do we need permission,
            // and then on Click of "OK" ask for the permission once again
            locationResultLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            locationResultLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestPermissionByUsingWhen(){
        // just FYI that, when == if else if else if .... else
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "Fine Location Permission Already Granted", Toast.LENGTH_LONG).show()
            }

            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "Coarse Location Permission Already Granted", Toast.LENGTH_LONG).show()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                /* In an educational UI, explain to the user why your app requires this
                permission for a specific feature to behave as expected. In this UI,
                include a "cancel" or "no thanks" button that allows the user to
                continue using your app without granting the permission.
                 */
                Toast.makeText(
                    this,
                    "This Fine Location Permission is required to give personalized experience",
                    Toast.LENGTH_LONG
                ).show()
                // show custom dialog explaining why do we need permission,
                // and then on Click of "OK" ask for the permission once again
                locationResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                /* In an educational UI, explain to the user why your app requires this
                permission for a specific feature to behave as expected. In this UI,
                include a "cancel" or "no thanks" button that allows the user to
                continue using your app without granting the permission.
                 */
                Toast.makeText(
                    this,
                    "This Coarse Location Permission is required to give personalized experience",
                    Toast.LENGTH_LONG
                ).show()
                // show custom dialog explaining why do we need permission,
                // and then on Click of "OK" ask for the permission once again
                locationResultLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            else -> {
                //locationResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestLocationPermissions(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED-> {
                Toast.makeText(this, "Fine Location Permission Already Granted", Toast.LENGTH_LONG).show()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                /* In an educational UI, explain to the user why your app requires this
                permission for a specific feature to behave as expected. In this UI,
                include a "cancel" or "no thanks" button that allows the user to
                continue using your app without granting the permission.
                 */
                Toast.makeText(this, "This Location Permission is required to give personalized experience", Toast.LENGTH_LONG).show()
                // show custom dialog explaining why do we need permission,
                // and then on Click of "OK" ask for the permission once again
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }

            else -> {
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestLocationPermissionsFromUtils(){
        requestLocationPermissionsByUtils(this, onPermissionResult)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    /*
    This fun is only intended for -> if we want multiple permissions, and all permissions are mandatory
    if even single permission is not provided out of those all, then it will show error and won't launch the intended feature
     */
    private fun requestMultiplePermissionByLoop(): Boolean{
        val listOfPermissionsNeeded = arrayListOf<String>()
        listOfPermissions.forEach {
            if(ContextCompat.checkSelfPermission(this, it) !=PackageManager.PERMISSION_GRANTED){
                listOfPermissionsNeeded.add(it)
            }
        }
        if(listOfPermissionsNeeded.isNotEmpty()){
            listOfPermissionsNeeded.forEach {
                if(shouldShowRequestPermissionRationale(it)){
                    // Show Common message in Dialog / Toast for all the permissions OR check if it == permission needed, then show dialog content acc. to that permission
                    // click ok in dialog, will ask for permission once again
                    Toast.makeText(this, "This Location Permission is required to give personalized experience", Toast.LENGTH_LONG).show()
                    locationResultLauncher.launch(it)
                }else{
                    locationResultLauncher.launch(it)
                }
            }
            return false
        }
        // app has all the permissions needed
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val onPermissionResult: (arrayOfPermissions: Array<String>) -> Unit = { arrayOfPermissions: Array<String> ->
        locationPermissionRequest.launch(arrayOfPermissions)
    }
}