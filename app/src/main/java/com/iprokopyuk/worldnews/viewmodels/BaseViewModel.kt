package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.iprokopyuk.worldnews.utils.LOG_TAG
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
 //   @Inject
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var internetDisposable: Disposable

    init {
        Log.d(LOG_TAG, "Block init BaseViewModel................!!!!!!!!!!!!!!!!!")
    }

    fun addToDisposable(disposable: Disposable) {
        //compositeDisposable.add(disposable)
    }

    override fun onCleared() {

        Log.d(
            LOG_TAG,
            "ViewModel Cleared........................................................!!!!!!!!!!"
        )

       // compositeDisposable.clear()
        //internetDisposable.dispose()
        super.onCleared()
    }
}