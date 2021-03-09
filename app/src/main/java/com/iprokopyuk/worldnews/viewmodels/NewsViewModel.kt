package com.iprokopyuk.worldnews.viewmodels

import androidx.lifecycle.ViewModel
import com.iprokopyuk.worldnews.data.remote.NewsRepository
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    fun getNews() {

    }
}