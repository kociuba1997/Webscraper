package com.newsscraper.services.apireceivers

interface GetPopularTagsReceiver {
    fun onGetPopularTagsSuccess(popularTags: List<String>) {}
    fun onGetPopularTagsError() {}
}