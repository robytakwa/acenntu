package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.ReportLaundry
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class ReportTopupRepository @Inject constructor(private val dataStoreManager: DataStoreManager) {

    suspend fun getAllReportTopup(startDate: String, endDate: String, outletId: String): BaseResponse<ResultData<ReportLaundry>> {
        val user = dataStoreManager.fetchInitialPreferences()
        return api.getReportLaporanTopup(
            token = "Bearer ${user.token}",
            startDate = startDate,
            endDate = endDate,
            outletId = outletId
        )
    }
}