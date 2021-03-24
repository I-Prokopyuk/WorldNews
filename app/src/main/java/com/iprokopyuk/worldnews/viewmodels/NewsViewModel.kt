package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.gson.Gson
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.repository.NewsRepository
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.models.NewsSource
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

    var _items: NotNullMutableLiveData<LiveData<PagedList<News>>>? = null
    val items: NotNullMutableLiveData<LiveData<PagedList<News>>>?
        get() = _items


    fun getNews(_category: String, _language: String) {

        category = _category
        language = _language


        if (internetConnection.value) {

            Log.d(LOG_TAG, "Internet true, get data from repository")

            _refreshing.value = true

            newsRepository.getNews(category, language, object : ICallbackResultString {
                override fun onResultCallback(valString: String) {
                    var gson = Gson()
                    var testModel = gson.fromJson(valString, NewsSource::class.java)

                    if (testModel.data.size > 0) {
                        updateLocalDB(
                            testModel.data,
                            object : ICallbackResultBoolean {
                                override fun onResultCallback(valBoolean: Boolean) {
                                    if (valBoolean) _items =
                                        NotNullMutableLiveData(getLivePagedListBuilder())
                                }

                                override fun onErrorCallback(result: String) {
                                    TODO("Not yet implemented")
                                }

                            })


                    } else {
                        Log.d(LOG_TAG, "No articles in this category")
                    }

                    _refreshing.value = false
                }

                override fun onErrorCallback(result: String) {
                    TODO("Not yet implemented")
                }
            })

        } else {
            _items = NotNullMutableLiveData(getLivePagedListBuilder())
        }

    }

    fun getLivePagedListBuilder() = LivePagedListBuilder(
        newsDao.getNews(category, language),
        3
    ).build()

    fun updateLocalDB(
        news: List<News>,
        callbackResult: ICallbackResultBoolean
    ) {
        newsRepository.updateLocalDB(news, category, language, callbackResult)
    }

    fun getRefresh() = getNews(category, language)
}