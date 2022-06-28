package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.ResetUserRequest
import com.mitralaundry.xpro.data.model.UserAccountRequest
import com.mitralaundry.xpro.data.model.response.*
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class UserRepository @Inject constructor(private val session: DataStoreManager) {

    suspend fun getListUser(): BaseResponse<ResultData<DataUser>> {
        val user = session.fetchInitialPreferences()
        return api.getListUser(
            token = "Bearer ${user.token}"

        )
    }

    suspend fun getUser(): List<UserResponse> {
        return api.getUser()
    }


    suspend fun getListUserSearch(name : String): BaseResponse<ResultData<DataUser>> {
        val user = session.fetchInitialPreferences()
        return api.getListUserSearch(
            token = "Bearer ${user.token}",
            name = name

        )
    }

    suspend fun addUser(userAccountRequest: UserAccountRequest): BaseResponse<ResultUser> {
        val user = session.fetchInitialPreferences()
        return api.addUser(
            token = "Bearer ${user.token}",
            userAccountRequest = userAccountRequest
        )
    }

    suspend fun resetUser(resetUserRequest: ResetUserRequest): BaseResponse<ResultUser> {
        val user = session.fetchInitialPreferences()
        return api.resetUser(
            token = "Bearer ${user.token}",
            resetUserRequest = resetUserRequest
        )
    }

    suspend fun saveSession(token: String) {
        session.saveToken(token)
    }
}