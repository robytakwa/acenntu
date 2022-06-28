package com.mitralaundry.xpro.data.network

import com.mitralaundry.xpro.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpBuilder = OkHttpClient.Builder()
    .addInterceptor(logger())
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .hostnameVerifier { _, _ -> true }


fun logger(): HttpLoggingInterceptor {
    val log = HttpLoggingInterceptor()
    log.level = HttpLoggingInterceptor.Level.BODY
    return log
}

val okHttpClient = okHttpBuilder.build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api: JituApi = retrofit.create(JituApi::class.java)