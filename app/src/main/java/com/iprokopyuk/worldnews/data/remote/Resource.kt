package com.iprokopyuk.worldnews.data.remote

data class Resource<ResultType>(
    var status: String,
    var data: ResultType? = null,
    var errorMessage: String? = null
)
