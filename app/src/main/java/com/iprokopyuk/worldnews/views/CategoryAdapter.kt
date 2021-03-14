package com.iprokopyuk.worldnews.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.databinding.ItemCategoryBinding
import com.iprokopyuk.worldnews.models.NewsCategory

class CategoryAdapter(
    val list: List<NewsCategory>
) : RecyclerView.Adapter<CategoryAdapter.ModelViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ModelViewHolder(
        DataBindingUtil.inflate<ItemCategoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_category,
            parent,
            false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.binding.item = list[position]
        holder.binding.executePendingBindings()
    }


    inner class ModelViewHolder(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}