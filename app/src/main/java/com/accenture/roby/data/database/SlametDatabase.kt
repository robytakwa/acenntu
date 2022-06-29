package com.accenture.roby.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.accenture.roby.data.database.dao.CustomerCardDao
import com.accenture.roby.data.database.dao.OutletDao
import com.accenture.roby.data.database.model.CustomerCard
import com.accenture.roby.data.database.model.Outlet

@Database(entities = [Outlet::class, CustomerCard::class], version = 2, exportSchema = false)
abstract class SlametDatabase : RoomDatabase() {
    abstract fun outletDao(): OutletDao
    abstract fun customerCardDao(): CustomerCardDao
}