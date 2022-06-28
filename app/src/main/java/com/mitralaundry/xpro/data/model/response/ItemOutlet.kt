package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ItemOutlet(
    val id : Int?,
    @SerializedName("outlet_id")
    val outletId: String,
    @SerializedName("merchant_id")
    val merchantId: String,
    val name: String,
    var selected : Boolean
)
