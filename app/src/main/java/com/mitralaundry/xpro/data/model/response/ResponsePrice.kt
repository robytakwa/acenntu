package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponsePrice(

	@field:SerializedName("mapping_id")
	val mappingId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("w_d_price")
	val wDPrice: String? = null,

	@field:SerializedName("mac")
	val mac: String? = null
)
