package com.example.loginuser

import android.view.PixelCopy.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServies {

    @POST("Login/Login")
  /*  suspend fun userLogin(
        @Body requsetBody: RequestModel

    ): Response<RequestModel>*/

    suspend fun userLogin(@Body request: LoginRequest): LoginResponse
}