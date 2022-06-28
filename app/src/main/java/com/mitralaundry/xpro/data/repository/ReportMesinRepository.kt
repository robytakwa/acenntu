package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.ReportMesin
import com.mitralaundry.xpro.data.model.UploadLaporanMesinRequest
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class ReportMesinRepository @Inject constructor(private val dataStoreManager: DataStoreManager) {

    suspend fun getReportMesinFilter(startDate : String, endDate: String, outletId: String): BaseResponse<ResultData<ReportMesin>> {
        val user = dataStoreManager.fetchInitialPreferences()
        return api.getReportLaporanMesinFilter(
            token = "Bearer ${user.token}",
            startDate = startDate,
            endDate = endDate,
            outletId = outletId
        )
    }



    suspend fun uploadLaporanMesin(uploadLaporanMesinRequest: UploadLaporanMesinRequest): BaseResponse<ResultData<ReportMesin>> {
        val user = dataStoreManager.fetchInitialPreferences()
        return api.uploadLaporanMesin(
            token = "Bearer ${user.token}",
            uploadLaporanMesinRequest = uploadLaporanMesinRequest

        )
    }

    suspend fun saveSession(token: String) {
        dataStoreManager.saveToken(token)
    }
}