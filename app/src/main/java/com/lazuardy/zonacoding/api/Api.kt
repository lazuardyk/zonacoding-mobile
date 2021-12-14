package com.lazuardy.zonacoding.api

import com.lazuardy.zonacoding.data.ArticlesResponseItem
import com.lazuardy.zonacoding.data.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("articlesall")
    fun getArticles(): Call<List<ArticlesResponseItem>>

    @GET("articles/9")
    fun getArticlesByQuery(@Query("q") query: String?): Call<SearchResponse>
}