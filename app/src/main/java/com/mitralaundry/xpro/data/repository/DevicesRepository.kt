package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.CreateDeviceRequest
import com.mitralaundry.xpro.data.model.Device
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.ResultDevice
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class DevicesRepository @Inject constructor(private val dataStore: DataStoreManager) {

    suspend fun getListDevices(): BaseResponse<ResultData<Device>> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.getListDevices<ResultData<Device>>(
            token = "Bearer ${userSession.token}",
            1,
            50,
            "",
            null
        )
        return result
    }

    suspend fun addDevice(createDeviceRequest: CreateDeviceRequest): BaseResponse<ResultDevice> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.addDevice<ResultDevice>(
            token = "Bearer ${userSession.token}",
            createDeviceRequest = createDeviceRequest
        )
        return result
    }

    suspend fun detailDevice(deviceId: String): BaseResponse<ResultDevice> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.detailDevice<ResultDevice>(
            token = "Bearer ${userSession.token}",
            deviceId = deviceId
        )
        return result
    }

    suspend fun updateDevice(createDeviceRequest: CreateDeviceRequest): BaseResponse<ResultDevice> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.updateDevice<ResultDevice>(
            token = "Bearer ${userSession.token}",
            createDeviceRequest = createDeviceRequest
        )
        return result
    }

    suspend fun saveSession(token: String) {
        dataStore.saveToken(token)
    }

}