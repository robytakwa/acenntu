package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("confirm_password")
	val confirmPassword: String? = null
)
