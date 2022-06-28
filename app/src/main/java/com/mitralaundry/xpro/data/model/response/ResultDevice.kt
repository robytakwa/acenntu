package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResultDevice(
    val type: String,
    val name: String,
    @SerializedName("serial_num")
    val serialNumber: String,
    val address: String?,
    val status: String,
    val mac: String?,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("access_token")
    val accessToken: String
)