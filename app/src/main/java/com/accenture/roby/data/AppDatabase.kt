package com.accenture.roby.data

import androidx.room.RoomDatabase

//@Database(entities = [UserLogin::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userLoginDao(): UserLoginDao
}