package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseProfile(


	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
