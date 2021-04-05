package com.iprokopyuk.worldnews.utils

import android.content.Context

fun getDrawableResourceId(context: Context, nameRes: String) = context.resources.getIdentifier(
    nameRes,
    "drawable",
    context.packageName
)

