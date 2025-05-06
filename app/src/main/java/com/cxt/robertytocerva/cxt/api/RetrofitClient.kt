package com.cxt.robertytocerva.cxt.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dd84-2806-103e-20-2f95-2429-2f80-2d07-f3f4.ngrok-free.app" // URL actual de ngrok

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}