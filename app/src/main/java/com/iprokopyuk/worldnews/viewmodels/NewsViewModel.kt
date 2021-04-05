package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.repository.NewsRepository
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.*
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRepository: NewsRepository,
) : BaseViewModel(
) {
    var category: String
    var language: String
    var countries: String

    var internetConnection: Boolean? = null
    var updatePagedList: Boolean = false

    private val boundaryCallback = NewsBoundaryCallback()
    private val callbackResult = CallbackResultNews()

    init {

        Log.d(LOG_TAG, "Block init NewsViewModel................!!!!!!!!!!!!!!!!!")

        category = DEFAULT_CATEGORY
        language = DEFAULT_LANGUAGE
        countries = DEFAULT_COUNTRIES

        compositeDisposable.add(
            reactiveNetworkObservable()
                .subscribe({ isConnected ->

                    internetConnection?.also {

                        internetConnection = isConnected

                        _internetConnectionStatus.value = internetConnection

                    } ?: run {

                        internetConnection = isConnected

                        if (isConnected) getNews(category, language, countries)

                        if (!isConnected) _internetConnectionStatus.value = false
                    }
                })
        )
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

    private val _uiEventClick = MutableLiveData<String?>()
    val uiEventClick: LiveData<String?>
        get() = _uiEventClick

    fun onClickItem(url: String?) {
        url.let { _uiEventClick.value = it }
    }

    fun getRefresh() = getNews(category, language, countries)

    fun getNews(_category: String, _language: String, _countries: String) {

        _refreshing.value = true

        updatePagedList =
            if (!_category.equals(category) || !_language.equals(language)) true else false

        category = _category
        language = _language
        countries = _countries

        internetConnection?.let {
            if (it) newsRepository.getNews(
                true,
                category,
                language,
                countries,
                callbackResult,
                compositeDisposable
            ) else callbackResult.onDataNotAvailable()
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
                if (it) newsRepository.getNews(
                    false,
                    category,
                    language,
                    countries,
                    callbackResult,
                    compositeDisposable
                )
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