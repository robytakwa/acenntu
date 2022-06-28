package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.AccountAdminRequest
import com.mitralaundry.xpro.data.model.ResetUserAdminRequest
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.DataUser
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.ResultUser
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class AccountAdminRepository @Inject constructor(private val session: DataStoreManager) {
    suspend fun getListAccountAdmin(): BaseResponse<ResultData<DataUser>> {
        val user = session.fetchInitialPreferences()
        return api.getListAccountAdmin(
            token = "Bearer ${user.token}",
            perPage = 2

        )
    }

    suspend fun getListAccountAdminSearch(name : String): BaseResponse<ResultData<DataUser>> {
        val user = session.fetchInitialPreferences()
        return api.getListAccountAdminSearch(
            token = "Bearer ${user.token}",
            name = name


        )
    }

    suspend fun resetUserAdmin(resetUserAdminRequest : ResetUserAdminRequest): BaseResponse<ResultUser> {
        val user = session.fetchInitialPreferences()
        return api.resetUserAdmin(
            token = "Bearer ${user.token}",
            resetUserAdminRequest = resetUserAdminRequest
        )
    }

    suspend fun createAccountAdmin(accountAdminRequest: AccountAdminRequest): BaseResponse<ResultUser> {
        val user = session.fetchInitialPreferences()
        return api.createAccountAdmin(
            token = "Bearer ${user.token}",
            accountAdminRequest = accountAdminRequest
        )
    }

    suspend fun saveSession(token: String) {
        session.saveToken(token)
    }

}