package com.iprokopyuk.worldnews.data.remote

import androidx.lifecycle.LiveData
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.remote.api.ApiServices
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val apiServices: ApiServices,
    private val compositeDisposable: CompositeDisposable
) {
    fun getNewsArticles(): LiveData<Resource<List<News>?>> {
        compositeDisposable.add(
            apiServices.getNews(API_KEY, "sports", "en")
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .retry(2)
                .
        )
    }
}