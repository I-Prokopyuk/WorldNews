package com.iprokopyuk.worldnews.data.remote

import android.util.Log
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.remote.api.ApiServices
import com.iprokopyuk.worldnews.utils.API_KEY
import com.iprokopyuk.worldnews.utils.LOG_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsDao: NewsDao,
    private val apiServices: ApiServices,
) {
    fun getNews() =
        apiServices.getNews(API_KEY, "sports", "en")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .retry(2)
            .subscribe({ listNews -> Log.d(LOG_TAG, listNews.toString()) }, { throwable ->
                Log.d(
                    LOG_TAG, throwable.message.toString()
                )
            })

}