package com.accenture.roby.data.network

import com.accenture.roby.data.model.response.*
import retrofit2.http.*

interface JituApi {

    @GET("users")
    suspend fun getUser(): List <UserResponse>

    @GET("users/{username}")
    suspend fun detailUser(@Path("username") username: String): UserDetailModel

}