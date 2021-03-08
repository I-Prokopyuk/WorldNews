package com.iprokopyuk.worldnews.utils

import android.content.Context

fun getDrawableResourceId(context: Context, nameRes: String): Int {
    return context.resources.getIdentifier(
        nameRes,
        "drawable",
        context.packageName
    )
}