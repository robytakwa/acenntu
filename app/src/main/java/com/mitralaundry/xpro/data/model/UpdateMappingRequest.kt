package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

data class UpdateMappingRequest(

    @field:SerializedName("pulse_dur_type")
    val pulseDurType: String? = null,

    @field:SerializedName("is_active")
    val isActive: Boolean? = null,

    @field:SerializedName("outlet_id")
    val outletId: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("pulse")
    val pulse: String? = null,

    @field:SerializedName("micon_id")
    val miconId: Int? = null,

    @field:SerializedName("time_dur_type")
    val timeDurType: String? = null,

    @field:SerializedName("machine_id")
    val machineId: Int? = null,

    @field:SerializedName("mapping_id")
    val mappingId: Int? = null,

    @field:SerializedName("time_dur")
    val timeDur: Int? = null,

    @field:SerializedName("pulse_dur")
    val pulseDur: Int? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("report_online")
    val reportOnline: Boolean? = null
)
