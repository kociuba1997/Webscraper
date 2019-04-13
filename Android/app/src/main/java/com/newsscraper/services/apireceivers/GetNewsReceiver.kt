package com.newsscraper.services.apireceivers

import com.newsscraper.transportobjects.NewsDTO

interface GetNewsReceiver {
    fun onGetNewsSuccess(news: List<NewsDTO>)
    fun onGetNewsError()
}