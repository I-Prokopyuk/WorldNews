package com.iprokopyuk.worldnews.viewmodels.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.iprokopyuk.worldnews.utils.LOG_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    init {
        Log.d(LOG_TAG, "Block init BaseViewModel................!!!!!!!!!!!!!!!!!")
    }

    fun reactiveNetworkObservable() = ReactiveNetwork.observeInternetConnectivity()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun onCleared() {

        Log.d(
            LOG_TAG,
            compositeDisposable.size()
                .toString() + "<<<<<<<<<<<<<<  ------- SIZE Disposable........."
        )

        Log.d(
            LOG_TAG,
            "ViewModel Cleared........................................................!!!!!!!!!!"
        )

        compositeDisposable.clear()

        Log.d(
            LOG_TAG,
            compositeDisposable.size()
                .toString() + "<<<<<<<<<<<<<<  ------- SIZE Disposable........."
        )
        super.onCleared()
    }
}