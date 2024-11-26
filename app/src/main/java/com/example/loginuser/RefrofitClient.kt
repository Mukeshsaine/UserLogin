package com.example.loginuser

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RefrofitClient {
    private const val baseUrl = "http://uatlims.apollohl.in/homecollection/api/"

   private val retrofit by lazy {
       Retrofit.Builder()
           .baseUrl(baseUrl)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
   }
    val api: ApiServies by lazy {
        retrofit.create(ApiServies::class.java)
    }

}