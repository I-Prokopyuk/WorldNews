package com.iprokopyuk.worldnews.data.remote

import com.iprokopyuk.worldnews.data.local.NewsDao
import com.iprokopyuk.worldnews.data.remote.api.ApiServices
import com.iprokopyuk.worldnews.utils.ICallbackResult
import javax.inject.Inject

class NewsRepository
@Inject constructor(
    private val newsDao: NewsDao,
    private val apiServices: ApiServices,
) {
    fun getNews(callbackResult: ICallbackResult) {

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
            "language": "en",
            "country": "us",
            "published_at": "2020-08-05T05:47:24+00:00"
        },{
            "author": "TMZ Staff_333",
            "title": "Rafael Nadal Pulls Out Of U.S. Open Over COVID-19 Concerns",
            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
            "source": "TMZ.com",
            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
            "category": "general",
            "language": "en",
            "country": "us",
            "published_at": "2020-08-05T05:47:24+00:00"
        }
    ]
}"""
        callbackResult.onResultCallback(result)
    }

//        apiServices.getNews(API_KEY, "sports", "en")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .unsubscribeOn(Schedulers.io())
//            .retry(2)
//            .subscribe({ listNews -> Log.d(LOG_TAG, listNews.toString()) }, { throwable ->
//                Log.d(
//                    LOG_TAG, throwable.message.toString()
//                )
//            })

}


//val jsonString =
//    """{
//    "pagination": {
//        "limit": 100,
//        "offset": 0,
//        "count": 100,
//        "total": 293
//    },
//    "data": [
//        {
//            "author": "TMZ Staff_111",
//            "title": "Rafael Nadal Pulls Out Of U.S. Open Over COVID-19 Concerns",
//            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
//            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
//            "source": "TMZ.com",
//            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
//            "category": "general",
//            "language": "en",
//            "country": "us",
//            "published_at": "2020-08-05T05:47:24+00:00"
//        },
//        {
//            "author": "TMZ Staff_222",
//            "title": "Rafael Nadal Pulls Out Of U.S. Open Over COVID-19 Concerns",
//            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
//            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
//            "source": "TMZ.com",
//            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
//            "category": "general",
//            "language": "en",
//            "country": "us",
//            "published_at": "2020-08-05T05:47:24+00:00"
//        },{
//            "author": "TMZ Staff_333",
//            "title": "Rafael Nadal Pulls Out Of U.S. Open Over COVID-19 Concerns",
//            "description": "Rafael Nadal is officially OUT of the U.S. Open ... the tennis legend said Tuesday it's just too damn unsafe for him to travel to America during the COVID-19 pandemic. \"The situation is very complicated worldwide,\" Nadal wrote in a statement. \"The…",
//            "url": "https://www.tmz.com/2020/08/04/rafael-nadal-us-open-tennis-covid-19-concerns/",
//            "source": "TMZ.com",
//            "image": "https://imagez.tmz.com/image/fa/4by3/2020/08/04/fad55ee236fc4033ba324e941bb8c8b7_md.jpg",
//            "category": "general",
//            "language": "en",
//            "country": "us",
//            "published_at": "2020-08-05T05:47:24+00:00"
//        }
//    ]
//}"""