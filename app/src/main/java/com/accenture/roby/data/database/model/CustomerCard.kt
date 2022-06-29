package com.accenture.roby.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author souttab
 * email : andiyulistyo@gmail.com
 * Created 25/05/2022 at 23:28
 */
@Entity(tableName = "customer_card", indices = [Index(value = ["member_id"], unique = true)])
data class CustomerCard(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val saldo: String,
    val username: String,
    @ColumnInfo(name = "merchant_id")
    val merchantId: String,
    @ColumnInfo(name = "member_id")
    val memberId: String,
    @ColumnInfo(name = "outlet_id")
    val outletId: String
)
