package com.newsscraper.services.apireceivers

interface GetTagsReceiver {
    fun onGetTagsSuccess(tags: List<String>)
    fun onGetTagsError()
}