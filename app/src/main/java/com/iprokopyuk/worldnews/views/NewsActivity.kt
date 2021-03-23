package com.iprokopyuk.worldnews.views

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ActivityNewsBinding
import com.iprokopyuk.worldnews.utils.extensions.initializingCategoryNavigation
import com.iprokopyuk.worldnews.views.base.BaseActivity
import kotlinx.android.synthetic.main.content_main.*


class NewsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bindingActivity: ActivityNewsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_news)

        initializingCategoryNavigation(this)

        bindingActivity.vm = newsViewModel
        bindingActivity.setLifecycleOwner(this)

        newsViewModel.internetConnection.observe(this, Observer { t ->
            if (t) {

                val snackbar = Snackbar.make(
                    container_info,
                    R.string.snackbar_text,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(
                        R.string.snackbar_action_text,
                        View.OnClickListener { newsViewModel.getRefresh() })

                snackbar.show()
            }
        })


    }
}