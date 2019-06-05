package com.newsscraper.services.apireceivers

interface PutTagsReceiver {
    fun onPutTagsSuccess()
    fun onPutTagsError()
}