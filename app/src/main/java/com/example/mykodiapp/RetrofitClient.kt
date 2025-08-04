package com.example.mykodiapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // !!! IMPORTANT: REPLACE WITH YOUR ACTUAL API URL !!!
    private const val BASE_URL = "https://teledax.zorstream.dpdns.org/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

