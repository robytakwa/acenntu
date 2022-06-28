package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResultOutlet(
    @SerializedName("merchant_id")
    val merchantId: String,
    @SerializedName("outlet_id")
    val outletId: String,
    val name: String,
    val phone: String,
    val address: String,
    val description: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("is_active")
    val isActive: String

)
