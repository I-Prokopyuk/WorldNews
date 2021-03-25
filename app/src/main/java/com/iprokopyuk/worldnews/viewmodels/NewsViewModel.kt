package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.repository.NewsRepository
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRepository: NewsRepository
) : BaseViewModel(
) {

    var category: String
    var language: String
    var connectionFlag: Boolean = false


    init {
        category = DEFAULT_CATEGORY
        language = DEFAULT_LANGUAGE

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isConnected ->
                _internetConnection.value = isConnected
                connectionFlag = true
                getNews(category, language)
            })
    }


    private var _internetConnection: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(true)
    val internetConnection: NotNullMutableLiveData<Boolean>
        get() = _internetConnection

    private val _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: NotNullMutableLiveData<Boolean>
        get() = _refreshing

    var _items: NotNullMutableLiveData<LiveData<PagedList<News>>?> = NotNullMutableLiveData(null)
    val items: NotNullMutableLiveData<LiveData<PagedList<News>>?>
        get() = _items


    var callbackResultNews = CallbackResultNews()

    fun getRefresh() = getNews(category, language)

    fun getNews(_category: String, _language: String) {

        _refreshing.value = true

        category = _category
        language = _language


        if (internetConnection.value == true) {

            Log.d(LOG_TAG, "Internet true, get data from repository")

            newsRepository.getNews(category, language, callbackResultNews)

        } else {

            _items.value = LivePagedListBuilder(
                newsDao.getNews(category, language),
                3
            ).build()

            Log.d(LOG_TAG, "Internet false, get data from DB offline mode")

            _refreshing.value = false

            Log.d(LOG_TAG, category + " " + language)
        }
    }

    fun getLivePagedListBuilder() = LivePagedListBuilder(
        newsDao.getNews(category, language),
        3
    ).build()

    inner class CallbackResultNews : ICallbackResultBoolean {
        override fun onResultCallback(_result: Boolean) {

            _items = NotNullMutableLiveData(getLivePagedListBuilder())

            _refreshing.value = false
        }

        override fun onErrorCallback(_error: String) {
            _refreshing.value = false
        }
    }
}