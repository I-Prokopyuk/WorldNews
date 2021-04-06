package com.iprokopyuk.worldnews.views

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ModelViewHolder<T : ViewDataBinding>(var binding: T) :
    RecyclerView.ViewHolder(binding.root) {
}