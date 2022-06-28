package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class Merchant(
    @SerializedName("merchant_id")
    val merchantId: String,
    val name: String,
    val phone: String,
    val address: String,
    val description: String?,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("is_active")
    val isActive: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("updated_by")
    val updatedBy: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("deleted_at")
    val deletedAt: String,
    @SerializedName("oultets")
    val outlets: MutableList<Outlet>
)