package com.iprokopyuk.worldnews.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "News")
data class News(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("author") var author: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("published_at")
    @ColumnInfo(name = "published_at")
    var publishedAt: String? = null

)