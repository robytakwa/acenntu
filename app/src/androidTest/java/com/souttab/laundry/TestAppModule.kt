package com.souttab.laundry

import android.content.Context
import androidx.room.Room
import com.mitralaundry.xpro.data.database.SlametDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("slamet_database")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context, SlametDatabase::class.java
        ).allowMainThreadQueries()
            .build()
}