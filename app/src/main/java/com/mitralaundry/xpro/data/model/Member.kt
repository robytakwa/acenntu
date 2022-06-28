package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

class Member(
    @SerializedName("outlet_id")
    val outletId: String,
    @SerializedName("member_id")
    val memberId : String,
    val name: String,
    val phone: String,
    @SerializedName("key_card")
    val keyCard: String,
    val saldo: Int
)