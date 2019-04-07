package com.example.newsscraper.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsscraper.data.model.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM News ORDER BY id DESC")
    fun getAll(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)

    @Query("DELETE FROM News")
    fun deleteAll()
}