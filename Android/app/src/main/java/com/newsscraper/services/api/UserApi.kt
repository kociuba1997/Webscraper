package com.newsscraper.services.api

import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.transportobjects.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable

interface UserApi {

    @POST("user")
    fun registerUser(@Body user: UserDTO)

    @GET("user/news")
    fun getNews(): Observable<NewsDTO>
}
