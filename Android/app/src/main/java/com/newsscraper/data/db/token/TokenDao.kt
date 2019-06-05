package com.newsscraper.data.db.token

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsscraper.data.model.Token

@Dao
interface TokenDao {
    @Query("SELECT * FROM Token ORDER BY id DESC")
    fun getToken(): Token

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(token: Token)

    @Query("DELETE FROM Token")
    fun delete()
}