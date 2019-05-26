package com.newsscraper.services

import com.newsscraper.services.api.LoginApi
import com.newsscraper.services.api.UserApi

class ServiceProvider {

    var token = ""

    fun userService(): UserApi = ServiceFactory.createRetrofitService(UserApi::class.java, Endpoints.BASE_URL, token)

    fun userRegisterService(): UserApi =
        ServiceFactory.createRetrofitService(UserApi::class.java, Endpoints.BASE_URL, token, false)

    fun loginService(): LoginApi =
        ServiceFactory.createRetrofitService(LoginApi::class.java, Endpoints.BASE_URL, token, false)
}
