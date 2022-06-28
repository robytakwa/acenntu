package com.mitralaundry.xpro.data.model.response

import com.google.gson.annotations.SerializedName

data class ResultData<T>(
    @SerializedName("current_page")
    val curentPage: Int,
    @SerializedName("first_page_url")
    val firstPageUrl: String,
    val from: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("last_page_url")
    val lastPageUrl: String,
    @SerializedName("next_page_url")
    val nextPageUrl: String?,
    val path: String,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("prev_page_url")
    val prevPageUrl: String?,
    val to: Int,
    val total: Int,
    val data: MutableList<T>,
    val links: MutableList<Link>,
)