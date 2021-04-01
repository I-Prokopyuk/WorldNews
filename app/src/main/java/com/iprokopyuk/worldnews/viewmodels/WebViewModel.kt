package com.iprokopyuk.worldnews.viewmodels

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.iprokopyuk.worldnews.utils.ICallbackResultBoolean
import com.iprokopyuk.worldnews.utils.NotNullMutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AppScoped
class WebViewModel @Inject constructor() : BaseViewModel(
) {
    private var internetConnection: Boolean? = null
    private val callbackResult = CallbackResultNews()

    init {
        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isConnected ->

                internetConnection?.also {

                    internetConnection = isConnected

                    _internetConnectionStatus.value = internetConnection

                } ?: run {

                    if (!isConnected) _internetConnectionStatus.value = false

                    internetConnection = isConnected
                }
            })
    }

    fun loadResourceFromUrl() {

    }

    private var _internetConnectionStatus: NotNullMutableLiveData<Boolean?> =
        NotNullMutableLiveData(null)
    val internetConnectionStatus: NotNullMutableLiveData<Boolean?>
        get() = _internetConnectionStatus


    private var _containerWithInformation: NotNullMutableLiveData<Boolean> =
        NotNullMutableLiveData(false)
    val containerWithInformation: NotNullMutableLiveData<Boolean>
        get() = _containerWithInformation


    inner class CallbackResultNews : ICallbackResultBoolean {
        override fun onDataAvailable() {

            _containerWithInformation.value = false

        }

        override fun onDataNotAvailable() {

            _containerWithInformation.value = true

        }
    }
}