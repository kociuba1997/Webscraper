package com.newsscraper.services.api

import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.transportobjects.TagsDTO
import com.newsscraper.transportobjects.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import rx.Observable

interface UserApi {

    @POST("user")
    fun registerUser(@Body user: UserDTO): Observable<Void>

    @GET("user/news")
    fun getNews(): Observable<List<NewsDTO>>

    @PUT("user/tags")
    fun putTags(@Body tags: TagsDTO): Observable<Void>

    @GET("user/tags")
    fun getTags(): Observable<List<String>>

}
