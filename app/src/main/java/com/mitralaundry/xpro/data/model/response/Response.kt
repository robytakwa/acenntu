package com.mitralaundry.xpro.data.model.response

data class Response<T>(
    val code: Int,
    val description: String,
    val result: T
)
