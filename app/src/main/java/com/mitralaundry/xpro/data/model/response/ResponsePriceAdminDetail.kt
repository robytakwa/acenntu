package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponsePriceAdminDetail(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("address")
	val address: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("mapping_id")
	val mappingId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("w_d_price")
	val wDPrice: String? = null
)


data class LinksItem(

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("url")
	val url: Any? = null
)

