package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResultMember(
    @SerializedName("member_id")
    val memberId: String,
    @SerializedName("merchant_id")
    val merchantId: String,
    @SerializedName("outlet_id")
    val outletId: String,
    val name: String,
    val phone: String,
    @SerializedName("key_card")
    val keyCard: String,
    @SerializedName("access_token")
    val accessToken: String
)
