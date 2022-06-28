package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseMapping(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("results")
	val results: Results? = null
)

data class Machine2(

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

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("w_d_price")
	val price: String,

	@field:SerializedName("merchant_id")
	val merchantId: String,

    @field:SerializedName("mapping_id")
	val mappingId: Int,

    @field:SerializedName("pulse_dur_type")
	val pulseDurType: String,

    @field:SerializedName("is_active")
	val isActive: String,

    @field:SerializedName("outlet_id")
	val outletId: String,

    @field:SerializedName("machine")
	val machine: Machine2? = null,

    @field:SerializedName("micon")
	val micon: Micon2? = null,

    @field:SerializedName("pulse")
	val pulse: String,

    @field:SerializedName("micon_id")
	val miconId: String,

    @field:SerializedName("time_dur_type")
	val timeDurType: String,

    @field:SerializedName("machine_id")
	val machineId: String,

    @field:SerializedName("time_dur")
	val timeDur: String,

    @field:SerializedName("pulse_dur")
	val pulseDur: String,

    @field:SerializedName("status")
	val status: String,

	@field:SerializedName("report_online")
	val reportOnline : Boolean

)

data class Micon2(

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

data class LinksItem2(

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("url")
	val url: Any? = null
)

data class Results(

    @field:SerializedName("per_page")
	val perPage: Int? = null,

    @field:SerializedName("data")
	val data: List<DataItem>? = null,

    @field:SerializedName("last_page")
	val lastPage: Int? = null,

    @field:SerializedName("next_page_url")
	val nextPageUrl: Any? = null,

    @field:SerializedName("prev_page_url")
	val prevPageUrl: Any? = null,

    @field:SerializedName("first_page_url")
	val firstPageUrl: String? = null,

    @field:SerializedName("path")
	val path: String? = null,

    @field:SerializedName("total")
	val total: Int? = null,

    @field:SerializedName("last_page_url")
	val lastPageUrl: String? = null,

    @field:SerializedName("from")
	val from: Int? = null,

    @field:SerializedName("links")
	val links: List<LinksItem2>? = null,

    @field:SerializedName("to")
	val to: Int? = null,

    @field:SerializedName("current_page")
	val currentPage: Int? = null
)
