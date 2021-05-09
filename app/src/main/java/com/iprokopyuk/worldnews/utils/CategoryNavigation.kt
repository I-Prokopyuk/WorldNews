package com.iprokopyuk.worldnews.utils

import android.content.Context
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.iprokopyuk.worldnews.models.NewsCategory
import javax.inject.Inject

@AppScoped
class CategoryNavigation @Inject constructor(private val context: Context) {

    private val listCategory = arrayListOf<NewsCategory>()

    init {
        listCategory.add(
            NewsCategory(
                "general",
                context.getString(R.string.category_name_general),
                getDrawableResourceId(
                    "category_general"
                )
            )
        )
        listCategory.add(
            NewsCategory(
                "business",
                context.getString(R.string.category_name_business),
                getDrawableResourceId("category_business")
            )
        )

        listCategory.add(
            NewsCategory(
                "health",
                context.getString(R.string.category_name_health),
                getDrawableResourceId("category_health")
            )
        )
        listCategory.add(
            NewsCategory(
                "sports",
                context.getString(R.string.category_name_sports),
                getDrawableResourceId("category_sports")
            )
        )
        listCategory.add(
            NewsCategory(
                "science",
                context.getString(R.string.category_name_science),
                getDrawableResourceId("category_science")
            )
        )
        listCategory.add(
            NewsCategory(
                "technology",
                context.getString(R.string.category_name_technology),
                getDrawableResourceId("categoty_technology")
            )
        )
        listCategory.add(
            NewsCategory(
                "entertainment",
                context.getString(R.string.category_name_entertainment),
                getDrawableResourceId("category_entertainment")
            )
        )
    }

    fun getDrawableResourceId(nameRes: String) = context.resources.getIdentifier(
        nameRes,
        "drawable",
        context.packageName
    )

    fun getListCategory(): List<NewsCategory> = listCategory
}