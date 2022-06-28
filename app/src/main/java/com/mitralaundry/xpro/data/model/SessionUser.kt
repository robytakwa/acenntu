package com.mitralaundry.xpro.data.model

data class SessionUser(
    val userName: String,
    var token: String,
    val role: String,
    val isLogin : Boolean = false
)
