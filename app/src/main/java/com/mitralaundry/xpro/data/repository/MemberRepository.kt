package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.Member
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.ItemOutlet
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.ResultMember
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject


class MemberRepository @Inject constructor(private val session: DataStoreManager) {
    suspend fun getListMember(outletId: String): BaseResponse<ResultData<ResultMember>> {
        val user = session.fetchInitialPreferences()
        return api.getListMember(
            token = "Bearer ${user.token}",
            outletId = outletId,
            perPage = 50,
        )
    }

    suspend fun addMember(member: Member): BaseResponse<ResultMember> {
        val user = session.fetchInitialPreferences()
        return api.addMember(
            token = "Bearer ${user.token}",
            member = member
        )
    }

    suspend fun getListOutlet(): BaseResponse<List<ItemOutlet>> {
        val user = session.fetchInitialPreferences()
        return api.getListOutlet(
            token = "Bearer ${user.token}"
        )
    }

    suspend fun updateMember(member: Member): BaseResponse<ResultMember> {
        val user = session.fetchInitialPreferences()
        return api.updateMember(
            token = "Bearer ${user.token}",
            member = member
        )
    }

    suspend fun saveSession(token: String) {
        session.saveToken(token)
    }
}