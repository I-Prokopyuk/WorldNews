package com.iprokopyuk.worldnews.views

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ActivityNewsBinding
import com.iprokopyuk.worldnews.utils.extensions.initializingCategoryNavigation
import com.iprokopyuk.worldnews.viewmodels.NewsViewModel
import com.iprokopyuk.worldnews.views.base.BaseActivity
import javax.inject.Inject


class NewsActivity : BaseActivity() {

//    val newsArticleViewModel: NewsViewModel by viewModels {
//        viewModelFactory
//    }

    @Inject
    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_news)

        var bindingActivity: ActivityNewsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_news)

        bindingActivity.vm = newsViewModel
        bindingActivity.setLifecycleOwner(this)

        initializingCategoryNavigation(this)

    }

}