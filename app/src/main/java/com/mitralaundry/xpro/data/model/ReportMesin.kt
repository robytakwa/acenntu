package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class ReportMesin(
    val counter: String,
    @SerializedName("curr_price")
    val currentPrice: String,
    val pendapatan: Int,
    val name: String,
    @SerializedName("created_at")
    val createdAt: String
)
