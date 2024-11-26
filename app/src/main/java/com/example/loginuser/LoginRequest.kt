package com.example.loginuser

data class LoginRequest(
    val AppVersion: Int,
    val batterypercentage: Int,
    val DeviceBrand: String,
    val deviceid: String,
    val DeviceModel: String,
    val latitude: String,
    val longitude: String,
    val MobileTokenID: String,
    val Password: String,
    val Username: String
)

data class LoginResponse(
    val status: Boolean,
    val message: String,
    val data: List<UserData>
)

data class UserData(
    val username: String,
    val PhlebotomistID: Int,
    val NAME: String,
    val Age: Int?,
    val Gender: String,
    val Mobile: String,
    val DeviceID: String,
    val PrinterMacAddress: String,
    val Token: String,
    val LoginExpiry: String
)
