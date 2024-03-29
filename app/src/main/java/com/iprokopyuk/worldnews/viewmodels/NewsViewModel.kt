package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.repository.NewsRepository
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.*
import com.iprokopyuk.worldnews.viewmodels.base.BaseViewModel
import javax.inject.Inject

@AppScoped
class NewsViewModel @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRepository: NewsRepository,
) : BaseViewModel(
) {
    var category: String
    var language: String
    var countries: String

    private var internetConnection: Boolean? = null
    private var updatePagedList: Boolean = false

    private val boundaryCallback = NewsBoundaryCallback()
    private val callbackResult = CallbackResultNews()

    init {
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

    private var _internetConnectionStatus: MutableLiveData<Boolean?> =
        MutableLiveData(null)
    val internetConnectionStatus: MutableLiveData<Boolean?>
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

    var uiEventClick = MutableLiveData<String?>()

    fun onClickItem(url: String?) {
        url.let { uiEventClick.value = it }
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
        }

        override fun onDataNotAvailable() {

            onUpdatePagedList()

            _refreshing.value = false
            _containerWithInformation.value = true
        }
    }
}