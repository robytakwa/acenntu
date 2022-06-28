package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponseCustom(
    val code: Int,
    val description: String,
    @SerializedName("results")
    val results: ResultDataCustom
)