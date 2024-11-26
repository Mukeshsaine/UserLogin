package com.example.loginuser

import android.content.Context
import android.os.Build
import android.provider.Settings

class DeviesDetials(private val context: Context) {

    // Function to get device model
    fun getDeviceModel(): String {
        return Build.MODEL
    }

    // Function to get device ID (Android ID)
    fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    // Function to get all device details as a map
    fun getDeviceDetails(): Map<String, String> {
        return mapOf(
            "DeviceModel" to getDeviceModel(),
            "DeviceID" to getDeviceId()
        )
    }
}