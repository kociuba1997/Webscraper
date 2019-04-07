package com.example.newsscraper.ui.newslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.newsscraper.NewsScraperApp
import com.example.newsscraper.data.model.News

class NewsListViewModel (application: Application) : AndroidViewModel(application) {

    private val newsRepository = getApplication<NewsScraperApp>().getNewsRepository()
    private val newsList = MediatorLiveData<List<News>>()

    init {
        getAllNews()
    }

    fun getNewsList(): LiveData<List<News>> {
        return newsList
    }

    private fun getAllNews() {
        newsList.addSource(newsRepository.getAllPeople()) { news ->
            newsList.postValue(news)
        }
    }
}