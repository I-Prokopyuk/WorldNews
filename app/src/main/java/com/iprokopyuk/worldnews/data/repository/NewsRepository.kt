package com.iprokopyuk.worldnews.data.repository

import android.util.Log
import com.google.gson.Gson
import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.remote.api.ApiServices
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.models.NewsSource
import com.iprokopyuk.worldnews.utils.ICallbackResultBoolean
import com.iprokopyuk.worldnews.utils.LOG_TAG
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsDao: NewsDao,
    private val apiServices: ApiServices,
) {
    fun gettttNews(
        category: String,
        language: String,
        callbackResult: ICallbackResultBoolean
    ) {

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

        //apiServices.getNews(API_KEY, "sports", "en")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .unsubscribeOn(Schedulers.io())
//            .retry(2)
//            .subscribe({ listNews -> Log.d(LOG_TAG, listNews.toString()) }, { throwable ->
//                Log.d(

        if (result != null) {

            Log.d(LOG_TAG, category + " | " + language)

            var gson = Gson()
            var testModel = gson.fromJson(result, NewsSource::class.java)

            val listNews = testModel.data

            updateLocalDB(category, language, listNews, object : ICallbackResultBoolean {
                override fun onDataAvailable() {
                    onDataAvailable(category, language)
                }

                override fun onDataAvailable(_category: String, _language: String) {
                    callbackResult.onDataAvailable(_category, _language)

                }

                override fun onDataNotAvailable() {
                    callbackResult.onDataNotAvailable()
                }
            })
        }


//                    LOG_TAG, throwable.message.toString()
//                )
//            })


    }

    fun updateLocalDB(
        category: String,
        language: String,
        listNews: List<News>,
        callbackResult: ICallbackResultBoolean
    ) {
        Completable.fromAction(Action { newsDao.deleteAndCreate(category, language, listNews) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(LOG_TAG, "News update in Local DB")
                callbackResult.onDataAvailable()
            },
                { error ->
                    Log.d(LOG_TAG, error.toString())
                    callbackResult.onDataNotAvailable()
                })
    }


}