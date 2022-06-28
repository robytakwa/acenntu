package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * @author souttab
 * email : andiyulistyo@gmail.com
 * Created 02/02/2022 at 16:30
 */

data class ResultUpdatePrice(
    @SerializedName("merchant_id")
    val merchantId: String,
    @SerializedName("outlet_id")
    val outletId: String,
    val name: String,
    val mac: String,
    @SerializedName("w_d_price")
    val wdPrice: Int,
    @SerializedName("updated_at")
    val updateAt: String,
    @SerializedName("access_token")
    val accessToken: String
)
