package com.iprokopyuk.worldnews.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ItemNewsBinding
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.views.NewsAdapter.ModelViewHolder

class NewsAdapter : PagedListAdapter<News, ModelViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ModelViewHolder(
            DataBindingUtil.inflate<ItemNewsBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_news,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {

        holder.binding.item = getItem(position)
    }

    inner class ModelViewHolder(var binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: News, newItem: News) =
                oldItem.title.equals(newItem.title)
        }
    }
}
