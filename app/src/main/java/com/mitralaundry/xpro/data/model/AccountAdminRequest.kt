package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

class AccountAdminRequest (
    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)