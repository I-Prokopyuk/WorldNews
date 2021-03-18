package com.iprokopyuk.worldnews.views

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ActivityNewsBinding
import com.iprokopyuk.worldnews.utils.extensions.initializingCategoryNavigation
import com.iprokopyuk.worldnews.views.base.BaseActivity


class NewsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bindingActivity: ActivityNewsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_news)

        initializingCategoryNavigation(this)

        bindingActivity.vm = newsViewModel
        bindingActivity.setLifecycleOwner(this)
    }

}