package com.iprokopyuk.worldnews.views

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ActivityWebBinding
import com.iprokopyuk.worldnews.viewmodels.WebViewModel
import com.iprokopyuk.worldnews.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_news.*
import javax.inject.Inject

class WebActivity : BaseActivity<ActivityWebBinding>() {
    @Inject
    lateinit var webViewModel: WebViewModel

    override fun getLayoutResId() = R.layout.activity_web

    override fun getViewForSnackbar() = layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = webViewModel
        binding.setLifecycleOwner(this)

        onObserveTointernetConnection({ webViewModel.loadResourceFromUrl() })
    }

    override fun getLiveDataInternetConnection(): LiveData<Boolean?> =
        webViewModel.internetConnectionStatus
}