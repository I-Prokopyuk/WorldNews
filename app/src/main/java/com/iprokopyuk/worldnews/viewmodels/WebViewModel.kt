package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.iprokopyuk.worldnews.utils.LOG_TAG
import com.iprokopyuk.worldnews.utils.NotNullMutableLiveData
import com.iprokopyuk.worldnews.viewmodels.base.BaseViewModel
import javax.inject.Inject

@AppScoped
class WebViewModel @Inject constructor() : BaseViewModel(

) {
    private var internetConnection: Boolean? = null

    init {
        Log.d(LOG_TAG, "Block init WebViewModel................!!!!!!!!!!!!!!!!!")

        compositeDisposable.add(
            reactiveNetworkObservable()
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
        )
    }

    fun loadResourceFromUrl() {
        url.value = url.value
        _refreshing.value = false
    }

    private var _internetConnectionStatus: MutableLiveData<Boolean?> =
        MutableLiveData(null)
    val internetConnectionStatus: MutableLiveData<Boolean?>
        get() = _internetConnectionStatus

    private var _containerWithInformation: NotNullMutableLiveData<Boolean> =
        NotNullMutableLiveData(false)
    val containerWithInformation: NotNullMutableLiveData<Boolean>
        get() = _containerWithInformation

    private var _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: NotNullMutableLiveData<Boolean>
        get() = _refreshing

    var url = MutableLiveData<String?>()
}