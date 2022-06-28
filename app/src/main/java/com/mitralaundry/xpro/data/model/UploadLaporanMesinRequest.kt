package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class UploadLaporanMesinRequest(

	@field:SerializedName("outlet_id")
	val outletId: String? = null,

	@field:SerializedName("curr_price")
	val currPrice: String? = null,

	@field:SerializedName("counter")
	val counter: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("mac")
	val mac: String? = null
)
