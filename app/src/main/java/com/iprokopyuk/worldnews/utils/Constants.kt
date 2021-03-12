package com.iprokopyuk.worldnews.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

//API
internal const val API_BASE_URL = "http://api.mediastack.com"
internal const val API_KEY = "c201076c808cf73820ebd0af73947373"

//DB
internal const val DB_VERSION = 1;
internal const val DB_NAME = "NewsDB";

//LOG
internal const val LOG_TAG = "myLogs"

@BindingAdapter("android:ImageResource")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
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


