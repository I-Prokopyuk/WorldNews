package com.iprokopyuk.worldnews.models

import com.google.gson.annotations.SerializedName

data class NewsSource(

    @SerializedName("pagination") val pagination: Pagination?,
    @SerializedName("data") val data: ArrayList<News>?
)