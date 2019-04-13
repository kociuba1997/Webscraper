package com.newsscraper.ui.newslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.newsscraper.NewsScraperApp
import com.newsscraper.data.model.News

class NewsListViewModel(application: Application) : AndroidViewModel(application) {

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