package com.iprokopyuk.worldnews.views

import android.os.Bundle
import androidx.activity.viewModels
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.utils.extensions.initializingCategoryNavigation
import com.iprokopyuk.worldnews.viewmodels.NewsViewModel
import com.iprokopyuk.worldnews.views.base.BaseActivity


class NewsActivity : BaseActivity() {

//    val newsArticleViewModel: NewsViewModel by viewModels {
//        viewModelFactory
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

     //   initializingCategoryNavigation(this)

       // newsArticleViewModel.getNews()
    }

}