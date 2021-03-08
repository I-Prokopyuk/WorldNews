package com.iprokopyuk.worldnews.models

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("limit") var limit: Int = 0,
    @SerializedName("offset") var offset: Int = 0,
    @SerializedName("count") var count: Int = 0,
    @SerializedName("total") var total: Int = 0
)