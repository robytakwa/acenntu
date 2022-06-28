package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

class ReportLaundry(
    @SerializedName("member_id")
    val memberId: String,
    @SerializedName("created_at")
    val createdDate: String,
    val name: String,
    val topup: Int
)