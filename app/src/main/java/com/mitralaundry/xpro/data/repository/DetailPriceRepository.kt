package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.UpdatePriceRequest
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.ResponsePrice
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.ResultUpdatePrice
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class DetailPriceRepository @Inject constructor(val data: DataStoreManager) {

    suspend fun getPriceList(outletId: String): BaseResponse<ResultData<ResponsePrice>> {
   val user = data.fetchInitialPreferences()
        return api.getPriceList(
            token =  "Bearer ${user.token}",
            outletId = outletId
        )
    }

    suspend fun updatePrice(updatePriceRequest: UpdatePriceRequest): BaseResponse<ResultUpdatePrice> {
        val user = data.fetchInitialPreferences()
        return api.updatePrice(
            token = "Bearer ${user.token}",
            updatePriceRequest = updatePriceRequest
        )
    }

    suspend fun saveSession(token: String) {
        data.saveToken(token)
    }
}