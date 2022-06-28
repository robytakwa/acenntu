package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class Micon(
    @SerializedName("device_id")
    val deviceId: String,
    val type: String,
    val name: String,
    @SerializedName("serial_num")
    val serialNumber: String,
    val address: String?,
    val mac: String?,
    val status: String,
    @SerializedName("is_active")
    val isActive: String
)
