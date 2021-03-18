package com.iprokopyuk.worldnews.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.views.NewsAdapter
import com.squareup.picasso.Picasso

//API
internal const val API_BASE_URL = "http://api.mediastack.com"
internal const val API_KEY = "c201076c808cf73820ebd0af73947373"

//DB
internal const val DB_VERSION = 1;
internal const val DB_NAME = "NewsDB";

//LOG
internal const val LOG_TAG = "myLogs"

@BindingAdapter("imageResource")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("refreshing")
fun SwipeRefreshLayout.refreshing(visible: Boolean) {
    isRefreshing = visible
}

@BindingAdapter("news")
fun setNews(view: RecyclerView, items: PagedList<News>?) {
    view.adapter?.run {
        if (this is NewsAdapter) this.submitList(items)
    } ?: run {
        NewsAdapter().apply {
            view.adapter = this
            this.submitList(items)
        }

    }
}

@BindingAdapter("android:src")
fun setImageWithPicasso(imageView: ImageView, url: String?) {

    url?.let { Picasso.get().load(url).into(imageView) }
}

//@BindingAdapter("android:data")
//fun setVpAdapter(viewPager: ViewPager2, newsCategory: NewsCategory) {
//
//    viewPager.adapter?.run {
//
//        CategoryAdapter(ListNewsCategory.getListForAdapter()).apply {
//            viewPager.adapter = this
//        }
//    }
//
//
//}


