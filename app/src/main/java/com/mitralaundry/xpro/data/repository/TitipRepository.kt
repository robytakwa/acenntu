package com.mitralaundry.xpro.data.repository

import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.TitipLaundry
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.ResultTitipLaundry
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import com.mitralaundry.xpro.util.GsonUtil
import javax.inject.Inject

class TitipRepository @Inject constructor(private val dataStoreManager: DataStoreManager) {

    suspend fun updateTitipLaundry(titipLaundry: TitipLaundry) {
        val userSession = dataStoreManager.fetchInitialPreferences()
        val result: BaseResponse<ResultTitipLaundry> = api.postTitipLaundry(
            token = "Bearer ${userSession.token}",
            currentSaldo = titipLaundry.currentSaldo,
            memberId = titipLaundry.memberId,
            price = titipLaundry.price
        )

        if (result.code == 201) {
            val newResult =
                GsonUtil.fromJson<ResultTitipLaundry>(Gson().toJson(result.results))
            dataStoreManager.saveToken(newResult.accessToken)
        } else if (result.code == 200) {
            // {"code":200,"description":"Method tidak valid","results":null}
            val newResult =
                GsonUtil.fromJson<ResultTitipLaundry>(Gson().toJson(result.results))
            dataStoreManager.saveToken(newResult.accessToken)
        }
    }
}
