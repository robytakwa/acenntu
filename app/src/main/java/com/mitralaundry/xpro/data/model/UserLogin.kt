package com.mitralaundry.xpro.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_login")
class UserLogin(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "role")
    val role: String,
    @ColumnInfo(name = "is_active")
    val isActive: String,
    @ColumnInfo(name = "token")
    val token: String
)