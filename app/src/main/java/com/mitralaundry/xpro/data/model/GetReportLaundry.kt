package com.mitralaundry.xpro.data.model

import com.google.gson.annotations.SerializedName

class GetReportLaundry(
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("outlet_id")
    val outletId: String
)