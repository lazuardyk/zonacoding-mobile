package com.lazuardy.zonacoding.api

import com.lazuardy.zonacoding.data.ArticlesResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("articlesall")
    fun getArticles(): Call<List<ArticlesResponseItem>>
}