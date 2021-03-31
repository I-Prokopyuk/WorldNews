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

    var internetConnection: Boolean? = null
    var updatePagedList: Boolean = false

    private val boundaryCallback = NewsBoundaryCallback()
    private val callbackResult = CallbackResultNews()

    init {
        category = DEFAULT_CATEGORY
        language = DEFAULT_LANGUAGE

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isConnected ->

                internetConnection?.also {

                    internetConnection = isConnected

                    _internetConnectionStatus.value = internetConnection

                } ?: run {

                    if (isConnected) getNews(category, language)

                    if (!isConnected) _internetConnectionStatus.value = false

                    internetConnection = isConnected
                }
            })
    }

    private var _internetConnectionStatus: NotNullMutableLiveData<Boolean?> =
        NotNullMutableLiveData(null)
    val internetConnectionStatus: NotNullMutableLiveData<Boolean?>
        get() = _internetConnectionStatus

    private var _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: NotNullMutableLiveData<Boolean>
        get() = _refreshing

    private var _items: NotNullMutableLiveData<LiveData<PagedList<News>>> =
        NotNullMutableLiveData(getNewLivePagedListBuilder())
    val items: NotNullMutableLiveData<LiveData<PagedList<News>>>
        get() = _items

    private var _containerWithInformation: NotNullMutableLiveData<Boolean> =
        NotNullMutableLiveData(false)
    val containerWithInformation: NotNullMutableLiveData<Boolean>
        get() = _containerWithInformation

    fun getRefresh() = getNews(category, language)

    fun getNews(_category: String, _language: String) {

        _refreshing.value = true

        updatePagedList =
            if (!_category.equals(category) || !_language.equals(language)) true else false

        category = _category
        language = _language

        internetConnection?.let {
            newsRepository.getNews(true, category, language, callbackResult)
        }
    }

    fun getNewLivePagedListBuilder() =
        LivePagedListBuilder(newsDao.getNews(category, language), PAGE_SIZE).setBoundaryCallback(
            boundaryCallback
        ).build()

    fun onUpdatePagedList() {
        if (updatePagedList) _items.value = getNewLivePagedListBuilder()
    }

    inner class NewsBoundaryCallback : PagedList.BoundaryCallback<News>() {

        override fun onItemAtEndLoaded(itemAtEnd: News) {
            super.onItemAtEndLoaded(itemAtEnd)

            Log.d(LOG_TAG, "End data  {{{{{{{{{{{{{{{{{{{{{{{")

            internetConnection?.let {
                if (it) newsRepository.getNews(false, category, language, callbackResult)
            }
        }
    }

    inner class CallbackResultNews : ICallbackResultBoolean {
        override fun onDataAvailable() {
            onUpdatePagedList()

            _refreshing.value = false
            _containerWithInformation.value = false

            Log.d(LOG_TAG, "Data Available <<<<<<<<<<<<<<<<<<<!!!!!!!!!!!!!!!!!!!!!")
        }

        override fun onDataNotAvailable() {

            onUpdatePagedList()

            _refreshing.value = false
            _containerWithInformation.value = true

            Log.d(LOG_TAG, "Data Not Available <<<<<<<<<<<<<<<<<<<!!!!!!!!!!!!!!!!!!!!!")
        }
    }
}