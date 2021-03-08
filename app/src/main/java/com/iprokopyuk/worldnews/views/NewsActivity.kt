package com.iprokopyuk.worldnews.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.utils.extensions.initializingCategoryNavigation


class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        initializingCategoryNavigation(this)
    }

}