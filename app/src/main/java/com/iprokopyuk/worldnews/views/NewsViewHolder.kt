package com.iprokopyuk.worldnews.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //    lateinit var newsTitle: String
//    lateinit var picasso: Picasso
//    lateinit var news: News

//    init {
//        picasso = Picasso.get()
//    }


    val newsTitle = itemView.news_title as TextView
    val newsImage = itemView.news_image as ImageView
}