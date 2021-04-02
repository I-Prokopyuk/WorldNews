package com.iprokopyuk.worldnews.utils

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.viewmodels.NewsViewModel
import com.iprokopyuk.worldnews.views.NewsAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

//API
internal const val API_BASE_URL = "http://api.mediastack.com"
internal const val API_KEY = "a39c5d527c17e3385bd2b992abb0e238"

//DB
internal const val DB_VERSION = 1;
internal const val DB_NAME = "NewsDB";

//Format date
internal const val DATE_FORMAT_FROM = "yyyy-MM-dd'T'hh:mm:ss+00:00"
internal const val DATE_FORMAT_TO = "MMM dd, yyyy hh:mm"

//Default
internal const val DEFAULT_CATEGORY = "general"
internal const val DEFAULT_LANGUAGE = "en"
internal const val DEFAULT_COUNTRIES = "us"

//LOG
internal const val LOG_TAG = "myLogs"

//Config pagedList
internal const val PAGE_SIZE = 15


@BindingAdapter("imageResource")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("refreshing")
fun SwipeRefreshLayout.refreshing(visible: Boolean) {
    isRefreshing = visible
}

@BindingAdapter("news", "vm")
fun setNews(view: RecyclerView, items: PagedList<News>?, vm: NewsViewModel) {

    if (items != null) {

        view.adapter?.run {

            if (this is NewsAdapter) {
                this.submitList(items)
            }
        } ?: run {
            NewsAdapter(vm).apply {
                view.adapter = this
                this.submitList(items)
            }
        }
    }
}

@BindingAdapter("android:src", "android:progressView")
fun setImageWithPicasso(imageView: ImageView, url: String?, progressBar: ProgressBar) {

    url?.let {

        progressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(it)
            .fit()
            .centerInside()
            .error(R.drawable.no_image)
//            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                }
            })
    }
}

@BindingAdapter("android:setDate")
fun parseDateFormat(textView: TextView, date: String?) {

    date?.let {

        val dateParse =
            SimpleDateFormat(
                DATE_FORMAT_TO,
                Locale.US
            ).format(SimpleDateFormat(DATE_FORMAT_FROM).parse(date))

        textView.text = dateParse
    }
}

@BindingAdapter("loadUrl")
fun WebView.setUrl(url: String) {
    this.setWebViewClient(WebViewClient())
    this.clearCache(true)
    this.loadUrl(url)
}



