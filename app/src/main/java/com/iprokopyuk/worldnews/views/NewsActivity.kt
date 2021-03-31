package com.iprokopyuk.worldnews.views

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ActivityNewsBinding
import com.iprokopyuk.worldnews.utils.extensions.initializingCategoryNavigation
import com.iprokopyuk.worldnews.viewmodels.NewsViewModel
import com.iprokopyuk.worldnews.views.base.BaseActivity
import javax.inject.Inject

class NewsActivity : BaseActivity<ActivityNewsBinding>() {
    @Inject
    lateinit var newsViewModel: NewsViewModel

    override fun getLayoutResId() = R.layout.activity_news

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = newsViewModel
        binding.setLifecycleOwner(this)

        initializingCategoryNavigation(this)

        onObserveTointernetConnection({ newsViewModel.getRefresh() })
    }

    override fun getLiveDataInternetConnection(): LiveData<Boolean?> =
        newsViewModel.internetConnectionStatus


}