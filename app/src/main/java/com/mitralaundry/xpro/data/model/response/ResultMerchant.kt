package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResultMerchant(
    @SerializedName("user_name")
    val picUserName: String,
    @SerializedName("user_email")
    val picUserEmail: String,
    @SerializedName("user_phone")
    val picUserPhone: String,
    @SerializedName("merchant_name")
    val merchantName: String,
    @SerializedName("merchant_phone")
    val merchantPhone: String,
    @SerializedName("merchant_address")
    val merchantAddress: String,
    @SerializedName("merchant_description")
    val merchantDescription: String,
    @SerializedName("outlet_name")
    val outletName: String,
    @SerializedName("outlet_phone")
    val outletPhone: String,
    @SerializedName("outlet_address")
    val outletAddress: String,
    @SerializedName("outlet_description")
    val outletDescription: String,
    @SerializedName("access_token")
    val accessToken: String
)
