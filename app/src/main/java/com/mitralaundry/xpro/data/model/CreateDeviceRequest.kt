package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class CreateDeviceRequest(

	@field:SerializedName("device_id")
	val deviceId: String? = null,


	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("is_active")
	val isActive: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("serial_num")
	val serialNum: String? = null,

	@field:SerializedName("mac")
	val mac: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
