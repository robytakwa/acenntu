package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResultMapping(
    @SerializedName("outlet_id")
    val outletId: String,
    @SerializedName("machine_id")
    val machineId : String,
    @SerializedName("micon_id")
    val miconId: String,
    val pulse: String,
    @SerializedName("pulse_dur_type")
    val pulseDurationType: String,
    @SerializedName("pulse_dur")
    val pulseDuration: String,
    @SerializedName("time_dur_type")
    val timeDurationType : String,
    @SerializedName("time_dur")
    val timeDuration: String,
    val status : String,
    @SerializedName("is_active")
    val isActive: String,
    val machine : Machine,
    val micon : Micon
)
