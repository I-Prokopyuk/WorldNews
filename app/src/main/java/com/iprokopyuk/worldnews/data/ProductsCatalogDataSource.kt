package com.iprokopyuk.worldnews.data

import androidx.paging.PositionalDataSource
import com.iprokopyuk.worldnews.models.News

class ProductsCatalogDataSource : PositionalDataSource<News>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<News>) {
        TODO("Not yet implemented")
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        TODO("Not yet implemented")
    }
}