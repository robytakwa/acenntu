package com.mitralaundry.xpro.ui.screen.merchant.member.detail

import com.mitralaundry.xpro.data.model.Member
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject


class DetailMemberRepository @Inject constructor(val data: DataStoreManager) {

    suspend fun getDetailMember(idMember: String): BaseResponse<Member> {
        val user = data.fetchInitialPreferences()
        return api.getDetailMember(
            token = "Bearer ${user.token}",
            memberId = idMember
        )
    }
}