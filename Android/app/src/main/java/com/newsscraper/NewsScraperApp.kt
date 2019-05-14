package com.newsscraper

import android.app.Application
import com.newsscraper.data.NewsRepository
import com.newsscraper.data.TokenRepository

class NewsScraperApp : Application() {

    fun getNewsRepository() = NewsRepository(this)
    fun getTokenRepository() = TokenRepository(this)
}
