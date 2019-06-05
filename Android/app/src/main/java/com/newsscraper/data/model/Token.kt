package com.newsscraper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Token(
    var token: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)