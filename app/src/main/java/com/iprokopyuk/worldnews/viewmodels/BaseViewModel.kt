package com.iprokopyuk.worldnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    @Inject
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var internetDisposable: Disposable

    fun addToDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        internetDisposable.dispose()
        super.onCleared()
    }
}