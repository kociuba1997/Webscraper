package com.newsscraper.services

import com.newsscraper.services.api.LoginApi
import com.newsscraper.services.api.UserApi

object ServiceProvider {

    val AUTHORIZATION_HEADER =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6ImhhbXVsZWMiLCJuYmYiOjE1NTQ3NTU0NzMsImV4cCI6MTU1NDc1NjY3MywiaWF0IjoxNTU0NzU1NDczfQ.P4KFU6wcWZBRxkI69DmNFCfIAm39RMiIzOJ1Brj6EtM"

    var userService: UserApi =
        ServiceFactory.createRetrofitService(UserApi::class.java, Endpoints.BASE_URL)

    var userRegisterService: UserApi =
        ServiceFactory.createRetrofitService(UserApi::class.java, Endpoints.BASE_URL, false)

    var loginService: LoginApi =
        ServiceFactory.createRetrofitService(LoginApi::class.java, Endpoints.BASE_URL, false)
}
