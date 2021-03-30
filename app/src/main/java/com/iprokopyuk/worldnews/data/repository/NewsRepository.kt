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

    private val callbackResultInRepository = CallbackResultNews()

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
        action: () -> Unit,
        callbackResult: ICallbackResultBoolean
    ): Disposable {
        return Completable.fromAction(action)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(LOG_TAG, "News update or insert Local DB")
                callbackResult.onDataAvailable()
            },
                { error ->
                    Log.d(LOG_TAG, error.toString())
                    callbackResult.onDataNotAvailable()
                })
    }

    fun getNews(
        _clearCache: Boolean,
        _internetConnection: Boolean,
        _category: String,
        _language: String,
        _callbackResult: ICallbackResultBoolean
    ) {
        clearCache = _clearCache
        category = _category
        language = _language
        callbackResultViewModel = _callbackResult

        Log.d(LOG_TAG, category + " " + language + "$$$$$$$$$$$$$$$$$$$$$$")

        when (_internetConnection) {
            true -> {
                Log.d(LOG_TAG, "Internet true")
                Log.d(LOG_TAG, "ClearCache === " + clearCache)
                if (clearCache) {
                    paginationOffset = 0
                    paginationLimit = PAGE_SIZE
                }
                getData()
            }
            false -> {
                Log.d(LOG_TAG, "Not Internet")
                callbackResultViewModel.onDataNotAvailable()
            }
        }
    }

    fun getData() {

        Log.d(
            LOG_TAG,
            paginationOffset.toString() + " " + paginationLimit.toString() + " <<<< SELECT"
        )

        getSingleApi(category, language, paginationOffset, paginationLimit)
            .subscribe({ response ->

                if (response.data?.size == 0) callbackResultViewModel.onDataNotAvailable()

                Log.d(LOG_TAG, "......................Further...................")

                if (response.pagination != null && response.data != null) {

                    paginationOffset += paginationLimit

                    (response.pagination.total - paginationOffset)?.also {

                        if (it < paginationLimit) paginationLimit = it
                    }


                    Log.d(LOG_TAG, response.data.toString() + "Respone <<<<<<<<<")

                    if (clearCache) {

                        Log.d(LOG_TAG, category + " " + language)

                        Log.d(LOG_TAG, "!!!!!!!!!!!!! Delete And Insert   !!!!!!!!!!!!")

                        completableFromAction({
                            actionDeleteAndInsertToLocalDB(
                                category,
                                language,
                                response.data
                            )
                        }, callbackResultInRepository)

                    } else {

                        Log.d(LOG_TAG, "!!!!!!!!!!!!! Insert   !!!!!!!!!!!!")

                        completableFromAction(
                            { actionInsertToLocalDB(response.data) }, callbackResultInRepository
                        )

                    }

                } else callbackResultViewModel.onDataNotAvailable()

            },
                { throwable ->
                    Log.d(LOG_TAG, "Error: " + throwable.message.toString())
                    callbackResultViewModel.onDataNotAvailable()

                })
    }


    inner class CallbackResultNews : ICallbackResultBoolean {
        override fun onDataAvailable() {
            callbackResultViewModel.onDataAvailable()
        }

        override fun onDataNotAvailable() {
            callbackResultViewModel.onDataNotAvailable()
        }
    }
}

val result = """{
    "pagination": {
        "limit": 100,
        "offset": 0,
        "count": 100,
        "total": 293
    },
    "data": [
        {
            "author": "TMZ Staff_111",
            "title": "Rafael Nadal Pulls Out Of U.S. Open Over COVID-19 Concerns",
            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
            "source": "TMZ.com",
            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
            "category": "general",
            "language": "en",
            "country": "us",
            "published_at": "2020-08-05T05:47:24+00:00"
        },
        {
            "author": "TMZ Staff_222",
            "title": "Rafael Nadal Pulls Out Of U.S. Open Over COVID-19 Concerns",
            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
            "source": "TMZ.com",
            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
            "category": "general",
            "language": null,
            "country": "us",
            "published_at": "2020-08-05T05:47:24+00:00"
        },{
            "author": "TMZ Staff_333",
            "title": null,
            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
            "source": "TMZ.com",
            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
            "category": "general",
            "language": "en",
            "country": "us",
            "published_at": null
        },{
            "author": "TMZ Staff_444",
            "title": null,
            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
            "source": "TMZ.com",
            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
            "category": "general",
            "language": "en",
            "country": "us",
            "published_at": null
        }
        ,{
            "author": "TMZ Staff_555",
            "title": null,
            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
            "source": "TMZ.com",
            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
            "category": "general",
            "language": "en",
            "country": "us",
            "published_at": null
        }
    ]
}"""


//        if (result != null) {
//
//            Log.d(LOG_TAG, category + " | " + language)
//
//            var gson = Gson()
//            var testModel = gson.fromJson(result, NewsSource::class.java)
//
//
//        }