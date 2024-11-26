package com.example.loginuser

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginuser.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000

    private lateinit var latitude: String
    private lateinit var longitude: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.login.setOnClickListener {
            login()
        }

              fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Check and request location permission
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, fetch location
            getLastLocation()
        }

    }

    // Handle the permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission granted, fetch location
            getLastLocation()
        } else {
            // Permission denied, handle appropriately
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        // Fetch the last known location
        val locationTask: Task<Location> = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { location ->
            if (location != null) {
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                Log.d("TAG","Latitude: $latitude, Longitude: $longitude")
            } else {
                Log.d("TAG","Location not available")
            }
        }
        locationTask.addOnFailureListener { exception ->
            println("Failed to get location: ${exception.message}")
        }
    }
    private fun login() {

        val detials = DeviesDetials(this)
        val deviceModel = detials.getDeviceModel()
        val deviceId = detials.getDeviceId()
        val userName: String = binding.name.text.toString().trim()
        val password: String = binding.password.text.toString().trim()
        val loginRequest = LoginRequest(
            AppVersion = 73,
            batterypercentage = 100,
            DeviceBrand = "google",
            deviceid = deviceId,
            DeviceModel = deviceModel,
            latitude = latitude,
            longitude = longitude,
            MobileTokenID = "cWEDhuFkQoqrS19HFQl9NI:APA91bE3JyU7xrKHg5fHculO8R_RPT25evgiY11fFF771UXXBsoJ7W3n8-b9Bvu4uMg9UWfF1m1V0NglbDIIBwhqcFq85i9f0zeTdeaOub7oVTjlhRoM4JA",
            Password = userName,
            Username = password
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RefrofitClient.api.userLogin(loginRequest)
                Log.d("TAG","Login Successful: ${response}")

                    if (response.status) {

                        response.data.forEach { userData ->
                            Log.d("TAG", "Username: ${userData.username}")
                            Log.d("TAG", "PhlebotomistID: ${userData.PhlebotomistID}")
                            Log.d("TAG", "Name: ${userData.NAME}")
                            Log.d("TAG", "Age: ${userData.Age}")
                            Log.d("TAG", "Gender: ${userData.Gender}")
                            Log.d("TAG", "Mobile: ${userData.Mobile}")
                            Log.d("TAG", "DeviceID: ${userData.DeviceID}")
                            Log.d("TAG", "PrinterMacAddress: ${userData.PrinterMacAddress}")
                            Log.d("TAG", "Token: ${userData.Token}")
                            Log.d("TAG", "LoginExpiry: ${userData.LoginExpiry}")
                        }

                    } else {
                        Log.d("TAG","Login Successful else : ${response.message}")
                    }


            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("TAG","Error: ${e.message}")
                }
            }
        }
    }
}