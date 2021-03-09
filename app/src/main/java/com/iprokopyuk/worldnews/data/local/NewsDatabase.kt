package com.iprokopyuk.worldnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iprokopyuk.worldnews.models.News
import com.iprokopyuk.worldnews.utils.DB_VERSION

@Database(entities = [News::class], version = DB_VERSION)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}