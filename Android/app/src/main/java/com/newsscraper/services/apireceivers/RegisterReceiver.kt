package com.newsscraper.services.apireceivers

interface RegisterReceiver {
    fun onRegisterSuccess()
    fun onRegisterError()
}