package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class DataUser(
    @SerializedName("outlet_id")
    val outletId: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("is_admin")
    val isAdmin: String,

)
