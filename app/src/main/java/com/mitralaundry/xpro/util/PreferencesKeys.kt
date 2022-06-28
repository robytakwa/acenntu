package com.mitralaundry.xpro.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val MERCHANT_ID = stringPreferencesKey("merchant_id")
    val OUTLET_ID = stringPreferencesKey("outlet_id")
    val TOKEN = stringPreferencesKey("token")
    val IS_LOGIN = booleanPreferencesKey("is_login")
    val NAME = stringPreferencesKey("name")
    val ROLE = stringPreferencesKey("role")
}