package com.newsscraper.services.api

import com.newsscraper.transportobjects.UserDTO
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface LoginApi {

    @POST("login")
    fun login(@Body user: UserDTO): Observable<String>
}