package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName


data class Login(
    val name: String,
    val email: String,
    val role: String,
    @SerializedName("is_active")
    val isActive: String,
    @SerializedName("access_token")
    val accessToken: String
)