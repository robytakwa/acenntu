package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

class ResultTitipLaundry(
    @SerializedName("member_id")
    val memberId: String,
    @SerializedName("curr_saldo")
    val currentSaldo: String,
    val price: String,
    @SerializedName("access_token")
    val accessToken: String
)
