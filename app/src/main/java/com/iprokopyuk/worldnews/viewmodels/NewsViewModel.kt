package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.repository.NewsRepository
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.DEFAULT_CATEGORY
import com.iprokopyuk.worldnews.utils.DEFAULT_LANGUAGE
import com.iprokopyuk.worldnews.utils.LOG_TAG
import com.iprokopyuk.worldnews.utils.NotNullMutableLiveData
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRepository: NewsRepository
) : BaseViewModel(
) {

    var category: String
    var language: String


    init {
        category = DEFAULT_CATEGORY
        language = DEFAULT_LANGUAGE
    }

    var tmpItems: LiveData<PagedList<News>> = LivePagedListBuilder(
//        newsDao.getNews(),
        newsDao.getNews(category, language),
        3
    ).build()

    private val _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: NotNullMutableLiveData<Boolean>
        get() = _refreshing

    private val _items: NotNullMutableLiveData<LiveData<PagedList<News>>> =
        NotNullMutableLiveData(tmpItems)
    val items: NotNullMutableLiveData<LiveData<PagedList<News>>>
        get() = _items

    fun getRefresh() = getNews(category, language)

    fun getNews(_category: String, _language: String) {

        category = _category
        language = _language

        Log.d(LOG_TAG, category + "<<<< category")

        Log.d(LOG_TAG, language + "<<<< language")

        _items.value = LivePagedListBuilder(
//        newsDao.getNews(),
            newsDao.getNews(category, language),
            3
        ).build()


//        _refreshing.value = true
//
//        //addToDisposable(newsRepository.getNews())
//
//        newsRepository.getNews(category, language, object : ICallbackResult {
//            override fun onResultCallback(valString: String) {
//                var gson = Gson()
//                var testModel = gson.fromJson(valString, NewsSource::class.java)
//
//                newsRepository.saveToLocalDB(testModel.data)
//
//                _refreshing.value = false
//            }
//
//            override fun onErrorCallback(result: String) {
//                TODO("Not yet implemented")
//            }
//        })
    }
}