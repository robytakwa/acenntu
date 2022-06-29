package com.mitralaundry.xpro.data.network

import com.mitralaundry.xpro.data.model.*
import com.mitralaundry.xpro.data.model.response.*
import retrofit2.http.*

interface JituApi {

    @GET("users")
    suspend fun getUser(): List <UserResponse>

    @GET("users/{username}")
    suspend fun detailUser(@Path("username") username: String): UserDetailModel

}