package com.iprokopyuk.worldnews.viewmodels.base

import androidx.lifecycle.ViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    fun reactiveNetworkObservable() = ReactiveNetwork.observeInternetConnectivity()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}