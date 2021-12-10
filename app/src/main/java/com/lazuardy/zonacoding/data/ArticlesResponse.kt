package com.lazuardy.zonacoding.data

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(

	@field:SerializedName("ArticlesResponse")
	val articlesResponse: List<ArticlesResponseItem?>? = null
)

data class ArticlesResponseItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)
