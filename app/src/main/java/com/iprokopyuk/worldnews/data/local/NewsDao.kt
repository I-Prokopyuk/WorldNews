package com.iprokopyuk.worldnews.data.local

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iprokopyuk.worldnews.models.News

@Dao
interface NewsDao {

//    @Query("SELECT * FROM news")
//    fun getNews(): DataSource.Factory<Int, News>

    @Query("SELECT * FROM news WHERE category = :category AND language= :language")
    fun getNews(category: String, language: String): DataSource.Factory<Int, News>

    @Query("DELETE FROM news WHERE category = :category AND language= :language")
    abstract fun deleteNews(category: String, language: String)

    @Query("DELETE FROM news")
    abstract fun deleteAllNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(listNews: List<News>): List<Long>
}