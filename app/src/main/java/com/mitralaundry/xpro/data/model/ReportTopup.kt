package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class ReportTopup(
    @SerializedName("member_id")
    val memberId: String,
    val name: String,
    val topup: String,
    @SerializedName("created_at")
    val createdAt: String,
)
