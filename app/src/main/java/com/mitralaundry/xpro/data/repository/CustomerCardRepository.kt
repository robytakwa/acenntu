package com.mitralaundry.xpro.data.repository

import com.mitralaundry.xpro.data.database.dao.CustomerCardDao
import com.mitralaundry.xpro.data.database.model.CustomerCard
import javax.inject.Inject

/**
 * @author souttab
 * email : andiyulistyo@gmail.com
 * Created 25/05/2022 at 23:38
 */
class CustomerCardRepository @Inject constructor(private val customerCardDao: CustomerCardDao) {

    suspend fun save(customerCard: CustomerCard) {
        customerCardDao.save(customerCard)
    }

    suspend fun getCustomerById(id: String): CustomerCard = customerCardDao.getCustomer(id)

    suspend fun delete(customerCard: CustomerCard) {
        customerCardDao.deleteByCard(customerCard)
    }

    suspend fun deleteAll() {
        customerCardDao.deleteAll()
    }
}