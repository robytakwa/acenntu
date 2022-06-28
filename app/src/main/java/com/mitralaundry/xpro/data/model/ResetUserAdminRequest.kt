package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class ResetUserAdminRequest(
    @field:SerializedName("user_id")
    val userId: String? = null
)
