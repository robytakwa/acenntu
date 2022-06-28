package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val dataStore: DataStoreManager
) {
    suspend fun isLogin(): Boolean {
        val user = dataStore.fetchInitialPreferences()
        return user.isLogin
    }
}