package com.iprokopyuk.worldnews.data.repository

import android.util.Log
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.remote.api.ApiServices
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.API_KEY
import com.iprokopyuk.worldnews.utils.ICallbackResultBoolean
import com.iprokopyuk.worldnews.utils.LOG_TAG
import com.iprokopyuk.worldnews.utils.PAGE_SIZE
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AppScoped
class NewsRepository
@Inject constructor(
    private val newsDao: NewsDao,
    private val apiServices: ApiServices
) {
    private var paginationOffset: Int = 0
    private var paginationLimit: Int = 0

    private var clearCache: Boolean = false
    private lateinit var category: String
    private lateinit var language: String
    private lateinit var countries: String
    private lateinit var callbackResultViewModel: ICallbackResultBoolean
    private lateinit var compositeDisposable: CompositeDisposable

    private fun getSingleApi(
        _category: String,
        _language: String,
        _countries: String,
        _paginationOffset: Int,
        _paginationLimit: Int
    ) =
        apiServices.getNews(
            API_KEY,
            _category,
            _language,
            _countries,
            _paginationOffset,
            _paginationLimit
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .retry(2)

    private fun actionDeleteAndInsertToLocalDB(
        _category: String,
        _language: String,
        _listNews: List<News>
    ) = newsDao.deleteAndInsert(_category, _language, _listNews)

    private fun actionInsertToLocalDB(_listNews: List<News>) = newsDao.insertNews(_listNews)

    private fun completableFromAction(
        action: () -> Unit
    ): Disposable {
        return Completable.fromAction(action)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callbackResultViewModel.onDataAvailable()
            },
                { throwable ->
                    //Log.d(LOG_TAG, "Error: " + throwable.message.toString())
                    callbackResultViewModel.onDataNotAvailable()
                })
    }

    fun getNews(
        _clearCache: Boolean,
        _category: String,
        _language: String,
        _countries: String,
        _callbackResult: ICallbackResultBoolean,
        _compositeDisposable: CompositeDisposable
    ) {
        clearCache = _clearCache
        category = _category
        language = _language
        countries = _countries
        callbackResultViewModel = _callbackResult
        compositeDisposable = _compositeDisposable

        if (clearCache) {
            paginationOffset = 0
            paginationLimit = PAGE_SIZE
        }
        getData()
    }

    private fun getData() {

        compositeDisposable.add(
            getSingleApi(category, language, countries, paginationOffset, paginationLimit)
                .subscribe(
                    { response ->

                        if (response == null || response?.pagination == null || response?.data?.size == 0) {
                            callbackResultViewModel.onDataNotAvailable()
                            return@subscribe
                        }

                        paginationOffset += paginationLimit

                        (response.pagination.total - paginationOffset)?.also {

                            if (it < paginationLimit) paginationLimit = it
                        }

                        if (clearCache) {

                            compositeDisposable.add(completableFromAction({
                                response?.data?.let {
                                    actionDeleteAndInsertToLocalDB(
                                        category,
                                        language,
                                        it
                                    )
                                }
                            }))

                        } else {

                            compositeDisposable.add(completableFromAction({
                                response.data?.let {
                                    actionInsertToLocalDB(
                                        it
                                    )
                                }
                            }))
                        }
                    },
                    { throwable ->
                        //Log.d(LOG_TAG, "Error: " + throwable.message.toString())
                        callbackResultViewModel.onDataNotAvailable()
                    })
        )
    }

}