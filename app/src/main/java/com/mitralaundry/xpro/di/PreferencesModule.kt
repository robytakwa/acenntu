package com.mitralaundry.xpro.di

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.mitralaundry.xpro.data.model.SessionUser
import com.mitralaundry.xpro.util.PreferencesKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore by preferencesDataStore(USER_PREFERENCES_NAME)

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveSession(session: SessionUser) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = session.token
            preferences[PreferencesKeys.NAME] = session.userName
            preferences[PreferencesKeys.ROLE] = session.role
            preferences[PreferencesKeys.IS_LOGIN] = session.isLogin
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    val getUserSession: Flow<SessionUser> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    private fun mapUserPreferences(preferences: Preferences): SessionUser {
        val token = preferences[PreferencesKeys.TOKEN] ?: ""
        val isLogin = preferences[PreferencesKeys.IS_LOGIN] ?: false
        val userName = preferences[PreferencesKeys.NAME] ?: ""
        val role = preferences[PreferencesKeys.ROLE] ?: ""

        return SessionUser(
            userName = userName,
            role = role,
            token = token,
            isLogin = isLogin
        )
    }

    suspend fun deleteAllSession() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun saveOutletId(outletId: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MERCHANT_ID] = outletId
        }
    }

    private fun getOutletId(preferences: Preferences): String {
        return preferences[PreferencesKeys.MERCHANT_ID] ?: ""
    }

}