package com.iprokopyuk.worldnews.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/v1/news?")
    //fun getArticles: LiveData<Resource<NewsSource>>(
    fun getArticles(
        @Query("access_key") key: String,
        @Query("categories") categories: String,
        @Query("languages") languages: String
    )
}
