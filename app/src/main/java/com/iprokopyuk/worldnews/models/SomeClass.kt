package com.iprokopyuk.worldnews.models

import androidx.lifecycle.MutableLiveData

class SomeClass<T:Any>(defaultValue:T):MutableLiveData<T>() {
    init {
        value = defaultValue
    }
    fun getData() = "Polygon 34"
}