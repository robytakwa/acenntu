package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("device_id")
    val deviceId: Int = 0,
    val type: String,
    val name: String,
    @SerializedName("serial_num")
    val serialNumber: String,
    val address: String = "",
    val mac: String = "",
    val status: String,
    @SerializedName("is_active")
    val isActive: Boolean
)
