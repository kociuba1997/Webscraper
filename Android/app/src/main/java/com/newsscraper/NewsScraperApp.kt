package com.newsscraper

import android.app.Application
import com.newsscraper.data.NewsRepository

class NewsScraperApp : Application() {

    fun getNewsRepository() = NewsRepository(this)
}
