package com.hadi.viewpager2carousel
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.databinding.BindingAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.iprokopyuk.worldnews.databinding.ItemCategoryBinding
//import com.iprokopyuk.worldnews.models.NewsCategory
//
//class CategorylAdapter(
//    val context: Context,
//    val list: List<NewsCategory>
//
//) : RecyclerView.Adapter<CategorylAdapter.ModelViewHolder>() {
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
//
//        val view = LayoutInflater.from(parent.context)
//        //.inflate(R.layout.item_category, parent, false)
//        val binding = ItemCategoryBinding.inflate(view, parent, false)
//        return ModelViewHolder(binding)
//    }
//
//    override fun getItemCount() = list.size
//
//    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) =
//        holder.bind(list[position])
//
//
//    inner class ModelViewHolder(val binding: ItemCategoryBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: NewsCategory) {
//            binding.item = item
//            binding.executePendingBindings()
//        }
//    }
//
//
//    companion object {
//        @BindingAdapter("app:src")
//        fun setImageResource(imageView: ImageView, resource: Int) {
//            imageView.setImageResource(resource)
//        }
//    }
//}