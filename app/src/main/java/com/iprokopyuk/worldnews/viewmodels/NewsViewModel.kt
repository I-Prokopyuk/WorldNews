package com.iprokopyuk.worldnews.viewmodels

import android.util.Log
import com.google.gson.Gson
import com.iprokopyuk.worldnews.data.remote.NewsRepository
import com.iprokopyuk.worldnews.models.NewsSource
import com.iprokopyuk.worldnews.utils.ICallbackResult
import com.iprokopyuk.worldnews.utils.LOG_TAG
import com.iprokopyuk.worldnews.utils.NotNullMutableLiveData
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    private val _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: NotNullMutableLiveData<Boolean>
        get() = _refreshing

    fun getNews() {

        Log.d(LOG_TAG, "getNews..............")

        _refreshing.value = true

        //addToDisposable(newsRepository.getNews())

        newsRepository.getNews(object : ICallbackResult {
            override fun onResultCallback(valString: String) {
                var gson = Gson()
                var testModel = gson.fromJson(valString, NewsSource::class.java)

                Log.d(LOG_TAG, testModel.toString() + "result from Repository")

                _refreshing.value = false
            }

            override fun onErrorCallback(result: String) {
                TODO("Not yet implemented")
            }

        })


    }
}