package com.iprokopyuk.worldnews.data.repository

import android.util.Log
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.remote.api.ApiServices
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.API_KEY
import com.iprokopyuk.worldnews.utils.ICallbackResultBoolean
import com.iprokopyuk.worldnews.utils.LOG_TAG
import com.iprokopyuk.worldnews.utils.PAGE_SIZE
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsDao: NewsDao,
    private val apiServices: ApiServices,
) {
    //Default pagination
    private var paginationOffset: Int = 0
    private var paginationLimit: Int = 0

    var clearCache: Boolean = false
    lateinit var category: String
    lateinit var language: String
    lateinit var callbackResultViewModel: ICallbackResultBoolean

    fun getSingleApi(
        _category: String,
        _language: String,
        _paginationOffset: Int,
        _paginationLimit: Int
    ) =
        apiServices.getNews(API_KEY, _category, _language, _paginationOffset, _paginationLimit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .retry(2)

    fun actionDeleteAndInsertToLocalDB(
        _category: String,
        _language: String,
        _listNews: List<News>
    ) = newsDao.deleteAndInsert(_category, _language, _listNews)

    fun actionInsertToLocalDB(_listNews: List<News>) = newsDao.insertNews(_listNews)

    fun completableFromAction(
        action: () -> Unit
    ): Disposable {
        return Completable.fromAction(action)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(LOG_TAG, "News update or insert Local DB")
                callbackResultViewModel.onDataAvailable()
            },
                { throwable ->
                    Log.d(LOG_TAG, "Error: " + throwable.message.toString())
                    callbackResultViewModel.onDataNotAvailable()
                })
    }


    fun getNews(
        _clearCache: Boolean,
        _category: String,
        _language: String,
        _callbackResult: ICallbackResultBoolean
    ) {
        clearCache = _clearCache
        category = _category
        language = _language
        callbackResultViewModel = _callbackResult

        if (clearCache) {
            paginationOffset = 0
            paginationLimit = PAGE_SIZE
        }
        getData()
    }

    fun getData() {

        getSingleApi(category, language, paginationOffset, paginationLimit)
            .subscribe(
                { response ->

                    if (response == null || response?.pagination == null || response?.data?.size == 0) {
                        callbackResultViewModel.onDataNotAvailable()
                        return@subscribe
                    }

                    Log.d(
                        LOG_TAG,
                        "..................................... Further .............................."
                    )

                    paginationOffset += paginationLimit

                    (response.pagination.total - paginationOffset)?.also {

                        if (it < paginationLimit) paginationLimit = it
                    }

                    if (clearCache) {

                        completableFromAction({
                            response?.data?.let {
                                actionDeleteAndInsertToLocalDB(
                                    category,
                                    language,
                                    it
                                )
                            }
                        })

                    } else {

                        completableFromAction(
                            { response.data?.let { actionInsertToLocalDB(it) } }
                        )
                    }
                },
                { throwable ->
                    Log.d(LOG_TAG, "Error: " + throwable.message.toString())
                    callbackResultViewModel.onDataNotAvailable()
                })
    }

}