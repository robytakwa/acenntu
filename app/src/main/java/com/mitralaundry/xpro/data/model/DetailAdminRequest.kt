package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class DetailAdminRequest(

	@field:SerializedName("outlet_id")
	val outletId: String? = null
)
