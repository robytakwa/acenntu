package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName


data class ResultUser(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("outlet_id")
	val outletId: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
