package com.iprokopyuk.worldnews.utils

interface ICallbackResultString {

    fun onResultCallback(valString: String)
    fun onErrorCallback(result: String)
}

interface ICallbackResultBoolean {

    fun onResultCallback(valBoolean: Boolean)
    fun onErrorCallback(result: String)
}