package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponsePriceAdmin(

	@field:SerializedName("outlet_id")
	val outletId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("merchant_id")
	val merchantId: String? = null
)
