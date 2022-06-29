package com.mitralaundry.xpro.data.repository
import com.mitralaundry.xpro.data.model.response.*
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class UserRepository @Inject constructor(private val session: DataStoreManager) {

    suspend fun getUser(): List<UserResponse> {
        return api.getUser()
    }

//    suspend fun getListUserSearch(name : String): BaseResponse<ResultData<DataUser>> {
//        val user = session.fetchInitialPreferences()
//        return api.getListUserSearch(
//            token = "Bearer ${user.token}",
//            name = name
//
//        )
//    }

}