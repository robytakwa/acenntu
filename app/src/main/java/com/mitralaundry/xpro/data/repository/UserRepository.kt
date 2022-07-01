package com.mitralaundry.xpro.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mitralaundry.xpro.data.model.UserDetailModel
import com.mitralaundry.xpro.data.model.response.*
import com.mitralaundry.xpro.data.network.JituApi
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class UserRepository @Inject constructor(private val session : DataStoreManager) {
//private val api = JituApi.create()
    suspend fun getUser(): List<UserResponse> {
        return api.getUser()
    }

    suspend fun getDetailUser(username : String): UserDetailModel {
        return api.detailUser(username)
    }

    fun getPagingUser() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { UserPagingResource(api) }
    ).liveData
}