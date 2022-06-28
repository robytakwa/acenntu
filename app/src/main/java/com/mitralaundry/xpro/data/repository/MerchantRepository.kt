package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.model.Merchant
import com.mitralaundry.xpro.data.model.response.BaseResponse
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.ResultMerchant
import com.mitralaundry.xpro.data.model.response.ResultOutlet
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import javax.inject.Inject

class MerchantRepository @Inject constructor(private val dataStore: DataStoreManager) {

    suspend fun saveNewToken(token: String) {
        dataStore.saveToken(token)
    }

    suspend fun getAllMerchant(
        page: Int,
        perPage: Int,
        sortBy: String?,
        order: String?,
        search: String?
    ): BaseResponse<ResultData<com.mitralaundry.xpro.data.model.response.Merchant>> {
        val userSession = dataStore.fetchInitialPreferences()
        val apai = api.merchant<ResultData<com.mitralaundry.xpro.data.model.response.Merchant>>(
            token = "Bearer ${userSession.token}",
            page = page,
            perPage = perPage,
            sortBy = sortBy,
            order = order,
            name = search
        )
        return apai
    }

    suspend fun merchantDetail(merchantId: String): BaseResponse<com.mitralaundry.xpro.data.model.response.Merchant> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.merchantDetail<com.mitralaundry.xpro.data.model.response.Merchant>(
            token = "Bearer ${userSession.token}",
            merchantId = merchantId
        )
        return result
    }

    suspend fun addMerchant(merchant: Merchant): BaseResponse<ResultMerchant> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.addMerchant<ResultMerchant>(
            token = "Bearer ${userSession.token}",
            userName = merchant.picUserName,
            userEmail = merchant.picUserEmail,
            userPhone = merchant.picUserPhone,
            merchantName = merchant.merchantName,
            merchantPhone = merchant.merchantPhone,
            merchantAddress = merchant.merchantAddress,
            merchantDescription = merchant.merchantDescription,
            outletName = merchant.outletName,
            outletPhone = merchant.outletPhone,
            outletAddress = merchant.outletAddress,
            outletDescription = merchant.outletDescription
        )
        return result
    }

    suspend fun addOutlet(
        merchantId: String,
        name: String,
        phone: String,
        address: String,
        description: String
    ): BaseResponse<ResultOutlet> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.addOutlet<ResultOutlet>(
            token = "Bearer ${userSession.token}",
            merchantId = merchantId,
            name = name,
            phone = phone,
            address = address,
            description = description
        )
        return result
    }

    suspend fun getDetailOutlet(outletId: String): BaseResponse<ResultOutlet> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.getDetailOutlet<ResultOutlet>(
            token = "Bearer ${userSession.token}",
            outletId = outletId
        )
        return result
    }

    suspend fun updateOutlet(
        outletId: String,
        name: String,
        phone: String,
        address: String,
        description: String
    ): BaseResponse<ResultOutlet> {
        val userSession = dataStore.fetchInitialPreferences()
        val result = api.updateOutlet<ResultOutlet>(
            token = "Bearer ${userSession.token}",
            outletId = outletId,
            name = name,
            phone = phone,
            address = address,
            description = description
        )
        return result
    }
}