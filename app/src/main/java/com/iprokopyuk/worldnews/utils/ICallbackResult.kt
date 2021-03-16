package com.iprokopyuk.worldnews.utils

interface ICallbackResult {

    fun onResultCallback(valString: String)
    fun onErrorCallback(result: String)
}