package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.UpdateProfileRequest
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.ResponseProfile
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class ProfileRepository @Inject constructor (val session : DataStoreManager) {

    suspend fun getProfileMerchant() : BaseResponse<ResultData<ResponseProfile>> {
      val user = session.fetchInitialPreferences()
        return api.getProfileMerchant(
            token =  "Bearer ${user.token}"
        )
    }

    suspend fun getProfileAdmin() : BaseResponse<ResultData<ResponseProfile>> {
        val user = session.fetchInitialPreferences()
        return api.getProfileAdmin(
            token =  "Bearer ${user.token}"
        )
    }

    suspend fun updateProfileMerchant(updateProfileMerchantRequest: UpdateProfileRequest) : BaseResponse<ResultData<ResponseProfile>> {
        val user = session.fetchInitialPreferences()
        return api.updateProfileMerchant(
            token = "Bearer ${user.token}",
            updateProfileMerchantRequest = updateProfileMerchantRequest
        )
    }

    suspend fun updateProfileAdmin(updateProfileAdminRequest: UpdateProfileRequest) : BaseResponse<ResultData<ResponseProfile>> {
        val user = session.fetchInitialPreferences()
        return api.updateProfileAdmin(
            token = "Bearer ${user.token}",
            updateProfileAdminRequest = updateProfileAdminRequest
        )
    }

    suspend fun saveSession(token: String) {
        session.saveToken(token)
    }

}
