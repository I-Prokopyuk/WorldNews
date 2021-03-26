package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.repository.NewsRepository
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AppScoped
class NewsViewModel @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRepository: NewsRepository
) : BaseViewModel(
) {

    var category: String
    var language: String
    var connectionCheck: Boolean = false


    init {
        category = DEFAULT_CATEGORY
        language = DEFAULT_LANGUAGE

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isConnected ->
                _internetConnection.value = isConnected
                if (!connectionCheck) getNews(category, language)
                connectionCheck = true
            })
    }

    private var _internetConnection: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(true)
    val internetConnection: NotNullMutableLiveData<Boolean>
        get() = _internetConnection

    private val _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: NotNullMutableLiveData<Boolean>
        get() = _refreshing

    private var _items: NotNullMutableLiveData<LiveData<PagedList<News>>?> =
        NotNullMutableLiveData(newLivePagedListBuilder())
    val items: NotNullMutableLiveData<LiveData<PagedList<News>>?>
        get() = _items

    var callbackResultNews = CallbackResultNews()

    fun getRefresh() = getNews(category, language)

    fun getNews(_category: String, _language: String) {

        newLivePagedListBuilder().

        when (internetConnection.value) {
            true -> {

                Log.d(LOG_TAG, "Internet - true...................<<<<<<<<<<<<<<<<<<<<")

                _refreshing.value = true

                newsRepository.gettttNews(_category, _language, callbackResultNews)
            }
            false -> {

                Log.d(LOG_TAG, "Internet - false.......................<<<<<<<<<<<<<<<<<<")

                updatePagedList(_category, _language)

                _refreshing.value = false
            }
        }

    }

    inner class CallbackResultNews : ICallbackResultBoolean {
        override fun onDataAvailable() {
            TODO("Not yet implemented")
        }

        override fun onDataAvailable(_category: String, _language: String) {
            updatePagedList(_category, _language)
            _refreshing.value = false
        }

        override fun onDataNotAvailable() {
            _refreshing.value = false
        }

    }

    fun updatePagedList(_category: String, _language: String) {

        Log.d(LOG_TAG, category + " " + language + " category language <<<<<<<<<<<<")

        Log.d(LOG_TAG, _category + " " + _language + " _category _language <<<<<<<<<<<<")

        if (!_category.equals(category) || !_language.equals(language)) {

            Log.d(LOG_TAG, "Update......................")

            category = _category
            language = _language

            _items.value = newLivePagedListBuilder()

        }
    }

    fun newLivePagedListBuilder() = LivePagedListBuilder(
        newsDao.getNews(category, language),
        3
    ).build()
}