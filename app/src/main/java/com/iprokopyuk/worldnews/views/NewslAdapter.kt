package com.hadi.viewpager2carousel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.models.News
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class NewsPagedListAdapter
//    (
//    val context: Context,
//    val list: List<News>,
//    val diffCallback: DiffUtil.ItemCallback<News>
//    val picasso: Picasso = Picasso.get()
//
//) : PagedListAdapter<News, NewsPagedListAdapter.ModelViewHolder>(News.DiffCallback) {
//
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ModelViewHolder {
//
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_news, parent, false)
//        return ModelViewHolder(view)
//    }
//
//    override fun getItemCount() = list.size
//
//
//    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
//
//        holder.newsTitle.text = list[position].title
//
//        picasso.load(list[position].image)
//            .fit()
//            .centerInside()
//            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//            .into(holder.newsImage, object : Callback {
//                override fun onSuccess() {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onError(e: Exception?) {
//                    TODO("Not yet implemented")
//                }
//            })
//    }
//
//    class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val newsTitle = itemView.news_title as TextView
//        val newsImage = itemView.news_image as ImageView
//    }
//}