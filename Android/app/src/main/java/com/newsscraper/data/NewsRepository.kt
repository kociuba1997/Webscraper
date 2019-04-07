package com.newsscraper.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.newsscraper.data.db.NewsDao
import com.newsscraper.data.db.NewsDatabase
import com.newsscraper.data.model.News

class NewsRepository(application: Application) {

    private val newsDao: NewsDao

    init {
        val peopleDatabase = NewsDatabase.getInstance(application)
        newsDao = peopleDatabase.newsDao()
    }

    fun getAllPeople(): LiveData<List<News>> {
        return newsDao.getAll()
    }

    fun insertPeople(people: News) {
        newsDao.insert(people)
    }
}