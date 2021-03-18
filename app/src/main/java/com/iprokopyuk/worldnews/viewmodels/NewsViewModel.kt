package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.gson.Gson
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.repository.NewsRepository
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.models.NewsSource
import com.iprokopyuk.worldnews.utils.ICallbackResult
import com.iprokopyuk.worldnews.utils.LOG_TAG
import com.iprokopyuk.worldnews.utils.NotNullMutableLiveData
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsDao: NewsDao,
    private val newsRepository: NewsRepository
) :
    BaseViewModel() {

    private val _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: NotNullMutableLiveData<Boolean>
        get() = _refreshing


    val items: LiveData<PagedList<News>> =
        LivePagedListBuilder(newsDao.getNews(),  /* page size */ 3).build()


    fun getNews() {

        _refreshing.value = true

        //addToDisposable(newsRepository.getNews())

        newsRepository.getNewsFromRemoteRepository(object : ICallbackResult {
            override fun onResultCallback(valString: String) {
                var gson = Gson()
                var testModel = gson.fromJson(valString, NewsSource::class.java)

                Log.d(LOG_TAG, testModel.data.toString() + "result from Remote repository")

                newsRepository.saveToLocalDB(testModel.data)

                _refreshing.value = false
            }

            override fun onErrorCallback(result: String) {
                TODO("Not yet implemented")
            }
        })
    }
}