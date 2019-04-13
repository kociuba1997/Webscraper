package com.newsscraper.services

import com.newsscraper.services.api.UserApi
import com.newsscraper.utils.safeLet

object ServiceProvider {

    val AUTHORIZATION_HEADER = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6ImhhbXVsZWMiLCJuYmYiOjE1NTQ3NTU0NzMsImV4cCI6MTU1NDc1NjY3MywiaWF0IjoxNTU0NzU1NDczfQ.P4KFU6wcWZBRxkI69DmNFCfIAm39RMiIzOJ1Brj6EtM"

    var userService: UserApi =
        ServiceFactory.createRetrofitService(UserApi::class.java, Endpoints.BASE_URL)

}
