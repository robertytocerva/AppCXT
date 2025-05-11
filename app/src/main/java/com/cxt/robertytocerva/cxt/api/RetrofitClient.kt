package com.cxt.robertytocerva.cxt.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://abdb-2806-103e-20-2f95-b91c-ad6e-dc99-ea19.ngrok-free.app" // URL actual de ngrok

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}