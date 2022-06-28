package com.mitralaundry.xpro.data

import androidx.room.Insert
import androidx.room.Query
import com.mitralaundry.xpro.data.model.UserLogin
import kotlinx.coroutines.flow.Flow

//@Dao
interface UserLoginDao {
    @Query("SELECT * FROM user_login")
    fun getUserLogin(): Flow<List<UserLogin>>

    @Insert
    suspend fun insert(userLogin: UserLogin)

    @Query("DELETE FROM user_login")
    fun deleteAll()
}