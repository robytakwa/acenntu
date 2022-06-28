package com.mitralaundry.xpro.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mitralaundry.xpro.data.database.model.Outlet

@Dao
interface OutletDao {
    @Insert
    suspend fun insertOutlet(outlet: Outlet)

    @Query("DELETE FROM outlet")
    suspend fun deleteAll()

    @Query("SELECT * FROM outlet")
    suspend fun getAllOutlet(): List<Outlet>

    @Query("UPDATE outlet SET selected = :selected")
    suspend fun updateAll(selected: Boolean)

    @Query("UPDATE outlet SET selected = :selected WHERE outlet_id = :outletId")
    suspend fun update(selected: Boolean, outletId: String)
}