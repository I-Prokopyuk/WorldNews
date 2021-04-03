package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.iprokopyuk.worldnews.utils.LOG_TAG
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    abstract fun getCompositeDisposable(): CompositeDisposable

    init {
        Log.d(LOG_TAG, "Block init BaseViewModel................!!!!!!!!!!!!!!!!!")
    }

    fun addToDisposable(disposable: Disposable) {
        getCompositeDisposable().add(disposable)
    }

    override fun onCleared() {

        Log.d(
            LOG_TAG,
            "ViewModel Cleared........................................................!!!!!!!!!!"
        )

        getCompositeDisposable().clear()
        super.onCleared()
    }
}