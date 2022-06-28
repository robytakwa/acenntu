package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class ResetUserRequest(

	@field:SerializedName("outlet_id")
	val outletId: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null
)
