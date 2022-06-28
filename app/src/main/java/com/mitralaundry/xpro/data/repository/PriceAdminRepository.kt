package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.DetailAdminRequest
import com.mitralaundry.xpro.data.model.UpdatePriceRequest
import com.mitralaundry.xpro.data.model.response.*
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class PriceAdminRepository @Inject constructor(val session : DataStoreManager) {


    suspend fun getPriceListAdmin(): BaseResponse<ResultData<ResponsePriceAdmin>> {
        val user = session.fetchInitialPreferences()
        return api.getPriceAdmin(
            token =  "Bearer ${user.token}",
            )
    }

    suspend fun getPriceListSearch(name:String): BaseResponse<ResultData<ResponsePriceAdmin>> {
        val user = session.fetchInitialPreferences()
        return api.getPriceAdminSearch(
            token =  "Bearer ${user.token}",
            name = name
        )
    }

    suspend fun getDetailPriceAdmin(detailAdminRequest: DetailAdminRequest) : BaseResponse<ResultData<ResponsePriceAdminDetail>> {
        val user = session.fetchInitialPreferences()
        return api.getDetailPriceAdmin(
            token =  "Bearer ${user.token}",
            detailAdminRequest = detailAdminRequest

        )
    }

    suspend fun updatePriceAdmin(updatePriceRequest: UpdatePriceRequest): BaseResponse<ResultUser> {
        val user = session.fetchInitialPreferences()
        return api.updatePriceAdmin(
            token = "Bearer ${user.token}",
            updatePriceRequest = updatePriceRequest
        )
    }

    suspend fun saveSession(token: String) {
        session.saveToken(token)
    }

}