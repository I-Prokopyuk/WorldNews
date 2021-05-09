package com.iprokopyuk.worldnews.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.iprokopyuk.worldnews.models.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news WHERE category = :category AND language= :language")
    fun getNews(category: String, language: String): DataSource.Factory<Int, News>

    @Transaction
    fun deleteAndInsert(category: String, language: String, listNews: List<News>) {
        deleteNews(category, language)
        insertNews(listNews)
    }

    @Query("DELETE FROM news WHERE category = :category AND language= :language")
    abstract fun deleteNews(category: String, language: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(listNews: List<News>)
}