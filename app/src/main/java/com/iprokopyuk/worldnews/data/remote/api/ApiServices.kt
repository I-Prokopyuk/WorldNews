package com.iprokopyuk.worldnews.data.remote.api

import com.iprokopyuk.worldnews.models.NewsSource
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/v1/news?sort=published_desc&")
    fun getNews(
        @Query("access_key") key: String,
        @Query("categories") categories: String,
        @Query("languages") languages: String,
        @Query("countries") countries: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<NewsSource>
}
