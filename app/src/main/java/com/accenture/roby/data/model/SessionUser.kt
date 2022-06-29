package com.accenture.roby.data.model

data class SessionUser(
    val userName: String,
    var token: String,
    val role: String,
    val isLogin : Boolean = false
)
