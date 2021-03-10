package com.hadi.viewpager2carousel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.models.NewsCategory
import kotlinx.android.synthetic.main.item_category.view.*

class CategorylAdapter(
    val context: Context,
    val list: List<NewsCategory>

) : RecyclerView.Adapter<CategorylAdapter.ModelViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {

        holder.categoryName.text = list[position].name

        holder.categoryImage.setImageResource(list[position].image)

    }

    class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName = itemView.category_title as TextView
        val categoryImage = itemView.category_image as ImageView
    }
}