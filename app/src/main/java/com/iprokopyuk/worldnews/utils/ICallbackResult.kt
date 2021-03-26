package com.iprokopyuk.worldnews.utils

interface ICallbackResultBoolean {

    fun onDataAvailable()
    fun onDataAvailable(_category: String, _language: String)
    fun onDataNotAvailable()
}