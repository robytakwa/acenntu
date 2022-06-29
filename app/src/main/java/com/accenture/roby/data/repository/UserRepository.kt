package com.accenture.roby.data.repository
import com.accenture.roby.data.model.response.*
import com.accenture.roby.data.network.api
import com.accenture.roby.di.DataStoreManager
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