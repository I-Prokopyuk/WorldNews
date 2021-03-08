package com.iprokopyuk.worldnews.utils

import android.content.Context
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.models.NewsCategory

object CategoryNavigation {

    fun getCategoryList(context: Context): List<NewsCategory> {

        var categoryList = arrayListOf<NewsCategory>()

        categoryList.add(
            NewsCategory(
                context.getString(R.string.category_name_general),
                getDrawableResourceId(
                    context, "category_general"
                )
            )
        )
        categoryList.add(
            NewsCategory(
                context.getString(R.string.category_name_business),
                getDrawableResourceId(context, "category_business")
            )
        )

        categoryList.add(
            NewsCategory(
                context.getString(R.string.category_name_health),
                getDrawableResourceId(context, "category_health")
            )
        )
        categoryList.add(
            NewsCategory(
                context.getString(R.string.category_name_sports),
                getDrawableResourceId(context, "category_sports")
            )
        )
        categoryList.add(
            NewsCategory(
                context.getString(R.string.category_name_science),
                getDrawableResourceId(context, "category_science")
            )
        )
        categoryList.add(
            NewsCategory(
                context.getString(R.string.category_name_technology),
                getDrawableResourceId(context, "categoty_technology")
            )
        )
        categoryList.add(
            NewsCategory(
                context.getString(R.string.category_name_entertainment),
                getDrawableResourceId(context, "category_entertainment")
            )
        )
        return categoryList
    }
}