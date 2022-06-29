package com.souttab.laundry

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.accenture.roby.data.database.SlametDatabase
import com.accenture.roby.data.database.dao.OutletDao
import com.accenture.roby.data.database.model.Outlet
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class UserDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_database")
    lateinit var database : SlametDatabase
    private lateinit var outletDao: OutletDao

    @Before
    fun setUp() {
        hiltRule.inject()
        outletDao = database.outletDao()
    }

    @After
    fun tearDown() {
        database.close()
    }


}