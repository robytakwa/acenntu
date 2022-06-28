package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.SessionUser
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class UserLoginRepository @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    suspend fun saveSession(sessionUser: SessionUser) {
        dataStoreManager.saveSession(session = sessionUser)
    }
}