package com.mitralaundry.xpro.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtil {

    inline fun <reified T> fromJson(json: String?): T {
        return Gson().fromJson<T>(json, object: TypeToken<T>(){}.type)
    }

//    inline fun <reified T> fromJson(json: String): T? {
//        return gson.fromJson(json, object : TypeToken<T>() {}.type)
//    }

    inline fun <reified T> mapToObject(map: Map<String, Any?>?, type: Class<T>): T? {
        if (map == null) return null

        val gson = Gson()
        val json = gson.toJson(map)
        return gson.fromJson(json, type)
    }

}