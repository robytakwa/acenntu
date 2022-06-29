package com.accenture.roby.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore by preferencesDataStore(USER_PREFERENCES_NAME)

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore


}