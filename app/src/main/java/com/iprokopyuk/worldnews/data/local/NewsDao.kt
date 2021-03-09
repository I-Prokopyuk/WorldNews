package com.iprokopyuk.worldnews.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iprokopyuk.worldnews.models.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getNews(): LiveData<List<News>>

    @Query("DELETE FROM news")
    abstract fun deleteAllNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(listNews: List<News>): List<Long>
}