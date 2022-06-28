package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

class TitipLaundry(
    @SerializedName("member_id")
    val memberId: String,
    @SerializedName("curr_saldo")
    val currentSaldo: String,
    val price : String
)