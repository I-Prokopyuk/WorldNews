package com.iprokopyuk.worldnews.models

import com.google.gson.annotations.SerializedName

data class NewsSource(

    @SerializedName("pagination") var pagination: Pagination,
    @SerializedName("data") var data: List<News> = emptyList()
)