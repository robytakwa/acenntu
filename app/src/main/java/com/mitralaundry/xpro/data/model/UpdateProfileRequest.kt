package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(

	@field:SerializedName("old_password")
	val oldPassword: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("password_confirmation")
	val passwordConfirmation: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
