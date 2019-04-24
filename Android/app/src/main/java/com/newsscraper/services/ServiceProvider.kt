package com.newsscraper.services

import com.newsscraper.services.api.LoginApi
import com.newsscraper.services.api.UserApi

object ServiceProvider {

    val AUTHORIZATION_HEADER =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IkphbiIsIm5iZiI6MTU1NTMyMzA2OCwiZXhwIjoxNTU1MzI0MjY4LCJpYXQiOjE1NTUzMjMwNjh9.Xh9Lhpblb9pO5lOfR2lcs1ogt2dsmYx_VqDUc3yKXuc"

    var userService: UserApi =
        ServiceFactory.createRetrofitService(UserApi::class.java, Endpoints.BASE_URL)

    var userRegisterService: UserApi =
        ServiceFactory.createRetrofitService(UserApi::class.java, Endpoints.BASE_URL, false)

    var loginService: LoginApi =
        ServiceFactory.createRetrofitService(LoginApi::class.java, Endpoints.BASE_URL, false)
}
