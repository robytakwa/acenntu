package com.accenture.roby.data.database.dao

import androidx.room.*
import com.accenture.roby.data.database.model.CustomerCard

/**
 * @author souttab
 * email : andiyulistyo@gmail.com
 * Created 25/05/2022 at 23:30
 */
@Dao
interface CustomerCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(customerCard: CustomerCard)

    @Query("SELECT * FROM customer_card WHERE member_id = :memberId")
    suspend fun getCustomer(memberId: String): CustomerCard

    @Query("DELETE FROM customer_card")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteByCard(customerCard: CustomerCard)
}