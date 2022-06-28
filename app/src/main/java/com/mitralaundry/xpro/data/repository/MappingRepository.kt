package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.CreateMappingRequest
import com.mitralaundry.xpro.data.model.Device
import com.mitralaundry.xpro.data.model.UpdateMappingRequest
import com.mitralaundry.xpro.data.model.response.*
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import com.mitralaundry.xpro.util.Constant
import javax.inject.Inject

class MappingRepository @Inject constructor(private val dataStore: DataStoreManager) {

    suspend fun getListMapping(
        page: Int,
        sortBy: String,
        outletid: String
    ): ResponseMapping {
        val user = dataStore.fetchInitialPreferences()
        return api.getListMapping(
            token = "Bearer ${user.token}",
            page = page,
            perPage = Constant.QUERY_PER_PAGE,
            sortBy = sortBy,
            outletId = outletid
        )
    }


    suspend fun getListDevicesMapping(): ResponseDeviceMapping {
        val user = dataStore.fetchInitialPreferences()
        return api.getListDevicesMapping(
            token = "Bearer ${user.token}"

        )
    }


    suspend fun updateMapping(updateMappingRequest: UpdateMappingRequest): BaseResponse<ResultUpdatePrice> {
        val user = dataStore.fetchInitialPreferences()
        return api.updateMapping(
            token = "Bearer ${user.token}",
            updateMappingRequest = updateMappingRequest
        )
    }

    suspend fun createMapping(createMappingRequest: CreateMappingRequest): BaseResponse<ResultUpdatePrice> {
        val user = dataStore.fetchInitialPreferences()
        return api.createMapping(
            token = "Bearer ${user.token}",
            createMappingRequest = createMappingRequest
        )
    }

    suspend fun saveSession(token: String) {
        dataStore.saveToken(token)
    }
}