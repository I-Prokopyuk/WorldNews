package com.iprokopyuk.worldnews.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ItemNewsBinding
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.viewmodels.NewsViewModel

class NewsAdapter(private val vm: NewsViewModel) :
    PagedListAdapter<News, ModelViewHolder<ItemNewsBinding>>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ModelViewHolder<ItemNewsBinding>(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ModelViewHolder<ItemNewsBinding>, position: Int) {

        holder.binding.item = getItem(position)
        holder.binding.vm = vm
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
        }
    }
}
