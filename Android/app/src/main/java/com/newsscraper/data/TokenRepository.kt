package com.newsscraper.data

import android.app.Application
import com.newsscraper.data.db.token.TokenDao
import com.newsscraper.data.db.token.TokenDatabase
import com.newsscraper.data.model.Token

class TokenRepository(application: Application) {

    private val tokenDao: TokenDao

    init {
        val peopleDatabase = TokenDatabase.getInstance(application)
        tokenDao = peopleDatabase.tokenDao()
    }

    fun getToken(): Token {
        return tokenDao.getToken()
    }

    fun insertToken(token: String) {
        tokenDao.insert(Token(token))
    }

    fun deleteToken() {
        tokenDao.delete()
    }

}