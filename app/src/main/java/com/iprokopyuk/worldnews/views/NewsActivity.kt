package com.iprokopyuk.worldnews.views

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ActivityNewsBinding
import com.iprokopyuk.worldnews.utils.LOG_TAG
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

        newsViewModel.internetConnection.observe(this, Observer { internetConnection ->
            internetConnection.let {

                Log.d(LOG_TAG, internetConnection.toString() + "!!!!")

                when (it) {
                    true -> {

                        if (newsViewModel.connectionFlag) {
                            showSnackbar(
                                resources.getString(R.string.snackbar_text),
                                resources.getString(R.string.snackbar_action_text)
                            )
                        }
                    }
                    false -> {
                        showToast(resources.getString(R.string.info_no_connection))
                    }
                }
            }
        })
    }


}