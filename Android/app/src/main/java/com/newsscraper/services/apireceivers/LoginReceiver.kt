package com.newsscraper.services.apireceivers

interface LoginReceiver {
    fun onLoginSuccess(token: String)
    fun onLoginError()
}