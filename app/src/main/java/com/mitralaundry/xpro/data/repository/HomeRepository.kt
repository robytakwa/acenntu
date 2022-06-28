package com.mitralaundry.xpro.data.repository

import com.google.gson.Gson
import com.mitralaundry.xpro.data.database.dao.OutletDao
import com.mitralaundry.xpro.data.database.model.Outlet
import com.mitralaundry.xpro.data.model.response.ItemOutlet
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.di.DataStoreManager
import com.mitralaundry.xpro.util.GsonUtil
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val dataStore: DataStoreManager,
    private val dao: OutletDao
) {

    suspend fun getListOutletDataStore(): List<ItemOutlet> {
        val outlets = dao.getAllOutlet()

        return if (outlets.isEmpty()) {
            getDataFromServer()
        } else {
            val list = arrayListOf<ItemOutlet>()
            outlets.forEach { value ->
                list.add(
                    ItemOutlet(
                        outletId = value.outletId,
                        merchantId = value.merchantId,
                        name = value.name,
                        selected = value.selected,
                        id = value.id
                    )
                )
            }
            list
        }
    }

    private suspend fun getDataFromServer(): List<ItemOutlet> {
        val session = dataStore.fetchInitialPreferences()
        val result = api.getListOutlet<ResultData<ItemOutlet>>(
            token = "Bearer ${session.token}"
        )
        val newResult =
            GsonUtil.fromJson<ResultData<ItemOutlet>>(Gson().toJson(result.results))

        val list = arrayListOf<ItemOutlet>()
        newResult.data.forEachIndexed { index, it ->
            if (index == 0) {
                dao.insertOutlet(Outlet(null, it.merchantId, it.outletId, it.name, true))
            } else {
                dao.insertOutlet(Outlet(null, it.merchantId, it.outletId, it.name, it.selected))
            }
        }

        val data = dao.getAllOutlet()
        data.forEach {
            list.add(
                ItemOutlet(
                    id = it.id,
                    outletId = it.outletId,
                    name = it.name,
                    merchantId = it.merchantId,
                    selected = it.selected
                )
            )
        }

        return list
    }

    suspend fun deleteOutlet() {
        dao.deleteAll()
    }

    suspend fun saveOutlet(merchantId: String) {
        dataStore.saveOutletId(merchantId)
    }

    suspend fun updateSelected(outletId: String) {
        println("repository update selected")
        println("update id ke $outletId")
        dao.updateAll(false)
        dao.update(true, outletId)
    }
}