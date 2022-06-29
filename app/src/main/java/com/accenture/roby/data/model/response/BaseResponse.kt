package com.accenture.roby.data.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    val code: Int,
    val description: String,
    @SerializedName("results")
    val results: T
)