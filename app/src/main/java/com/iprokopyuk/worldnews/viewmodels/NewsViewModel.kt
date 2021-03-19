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
import java.text.SimpleDateFormat
import java.util.*
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

                val date =
                    SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(testModel.data[0].publishedAt)

                val dateParse =
                    SimpleDateFormat("MMM dd, yyyy", Locale.US).format(date)



                val dateTime =
                    SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss+00:00").parse(testModel.data[0].publishedAt)


                val dateTimeParse =
                    SimpleDateFormat("MMM dd, yyyy hh:mm:ss").format(dateTime)



                Log.d(LOG_TAG, date.toString() + " date from parse")

                Log.d(LOG_TAG, dateParse.toString() + " date from foramt")

                Log.d(LOG_TAG, dateTime.toString() + " date from foramt 2")

                Log.d(LOG_TAG, dateTimeParse.toString() + " date from foramt 2")

                newsRepository.saveToLocalDB(testModel.data)

                _refreshing.value = false
            }

            override fun onErrorCallback(result: String) {
                TODO("Not yet implemented")
            }
        })
    }
}