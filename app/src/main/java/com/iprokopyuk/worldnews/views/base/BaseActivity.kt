package com.iprokopyuk.worldnews.views.base

import com.iprokopyuk.worldnews.viewmodels.NewsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var newsViewModel: NewsViewModel
}