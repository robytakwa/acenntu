package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class UpdatePriceRequest(

	@field:SerializedName("mapping_id")
	val mappingId: Int? = null,

	@field:SerializedName("w_d_price")
	val wDPrice: String? = null
)
