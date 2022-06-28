package com.mitralaundry.xpro.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mitralaundry.xpro.data.database.dao.CustomerCardDao
import com.mitralaundry.xpro.data.database.dao.OutletDao
import com.mitralaundry.xpro.data.database.model.CustomerCard
import com.mitralaundry.xpro.data.database.model.Outlet

@Database(entities = [Outlet::class, CustomerCard::class], version = 2, exportSchema = false)
abstract class SlametDatabase : RoomDatabase() {
    abstract fun outletDao(): OutletDao
    abstract fun customerCardDao(): CustomerCardDao
}