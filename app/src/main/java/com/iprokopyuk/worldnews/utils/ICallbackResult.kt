package com.iprokopyuk.worldnews.utils

interface ICallbackResultBoolean {

    fun onResultCallback(_result: Boolean)
    fun onErrorCallback(_result: String)
}