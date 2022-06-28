package com.souttab.laundry

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.mitralaundry.xpro.data.database.SlametDatabase
import com.mitralaundry.xpro.data.database.dao.OutletDao
import com.mitralaundry.xpro.data.database.model.Outlet
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

    @Test
    suspend fun testTestInsertUser() {
        val outlet = Outlet(1, "MDI-001", "OUT-001", "Joko Laundry")

        outletDao.insertOutlet(outlet)
        val allOutlet = outletDao.getAllOutlet().count()
        Assert.assertEquals(1, allOutlet)
    }
}