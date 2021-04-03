package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.iprokopyuk.worldnews.di.scopes.ActivityScoped
import com.iprokopyuk.worldnews.utils.LOG_TAG
import com.iprokopyuk.worldnews.utils.NotNullMutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WebViewModel @Inject constructor() : BaseViewModel(
) {
    private var internetConnection: Boolean? = null

    init {
        Log.d(LOG_TAG, "Block init WebViewModel................!!!!!!!!!!!!!!!!!")


        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isConnected ->

                internetConnection?.also {

                    if (isConnected) _containerWithInformation.value = false

                    internetConnection = isConnected

                    _internetConnectionStatus.value = internetConnection

                } ?: run {

                    if (!isConnected) {
                        _internetConnectionStatus.value = false

                        _containerWithInformation.value = true
                    }
                    internetConnection = isConnected
                }
            })
    }

    fun loadResourceFromUrl() {
        url.value = url.value
        _refreshing.value = false
    }

    private var _internetConnectionStatus: NotNullMutableLiveData<Boolean?> =
        NotNullMutableLiveData(null)
    val internetConnectionStatus: NotNullMutableLiveData<Boolean?>
        get() = _internetConnectionStatus

    private var _containerWithInformation: NotNullMutableLiveData<Boolean> =
        NotNullMutableLiveData(false)
    val containerWithInformation: NotNullMutableLiveData<Boolean>
        get() = _containerWithInformation

    private var _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: NotNullMutableLiveData<Boolean>
        get() = _refreshing

    var url = MutableLiveData<String?>()
    override fun getCompositeDisposable(): CompositeDisposable {
        TODO("Not yet implemented")
    }
}