package com.mitralaundry.xpro.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outlet")
data class Outlet(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,
    @ColumnInfo(name = "merchant_id")
    val merchantId: String,
    @ColumnInfo(name = "outlet_id")
    val outletId : String,
    val name : String,
    val selected : Boolean
)
