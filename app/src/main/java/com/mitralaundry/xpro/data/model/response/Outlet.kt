package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class Outlet(
    @SerializedName("merchant_id")
    val merchantId: String,
    @SerializedName("outlet_id")
    val outletId : String,
    val name: String,
    val phone: String,
    val address: String,
    val description: String,
    @SerializedName("is_active")
    val isActive: String
)
