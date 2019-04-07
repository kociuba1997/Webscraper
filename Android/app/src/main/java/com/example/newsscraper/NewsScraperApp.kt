package com.example.newsscraper

import android.app.Application
import com.example.newsscraper.data.NewsRepository

class NewsScraperApp : Application() {

    fun getNewsRepository() = NewsRepository(this)
}
