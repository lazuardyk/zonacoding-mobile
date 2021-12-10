package com.lazuardy.zonacoding.api

import com.lazuardy.zonacoding.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}