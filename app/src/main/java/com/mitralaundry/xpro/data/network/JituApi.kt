package com.mitralaundry.xpro.data.network

import com.mitralaundry.xpro.data.model.response.*
import retrofit2.http.*

interface JituApi {

    @GET("users")
    suspend fun getUser(): List <UserResponse>

}