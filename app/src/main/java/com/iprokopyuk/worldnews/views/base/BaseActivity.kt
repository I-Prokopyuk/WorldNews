package com.iprokopyuk.worldnews.views.base

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.iprokopyuk.worldnews.viewmodels.NewsViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var newsViewModel: NewsViewModel

    fun showSnackbar(text: String, textAction: String) {
        val snackbar = Snackbar.make(
            container_info,
            text,
            Snackbar.LENGTH_LONG
        )
            .setAction(
                textAction,
                View.OnClickListener { newsViewModel.getRefresh() })

        snackbar.show()
    }

    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}