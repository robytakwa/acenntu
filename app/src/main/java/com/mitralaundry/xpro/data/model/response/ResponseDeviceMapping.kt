package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseDeviceMapping(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem>? = null
)

data class ResultsItem(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("is_active")
	val isActive: String? = null,

	@field:SerializedName("device_id")
	val deviceId: Int? = null,

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
