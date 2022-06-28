package com.mitralaundry.xpro.di

import android.content.Context
import androidx.room.Room
import com.mitralaundry.xpro.data.database.SlametDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        SlametDatabase::class.java,
        "slamet_database"
    ).build()

    @Singleton
    @Provides
    fun provideOutletDao(database: SlametDatabase) = database.outletDao()

    @Singleton
    @Provides
    fun provideCustomerCardDao(database: SlametDatabase) = database.customerCardDao()
}