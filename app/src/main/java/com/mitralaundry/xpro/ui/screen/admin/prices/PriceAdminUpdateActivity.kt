package com.mitralaundry.xpro.ui.screen.admin.prices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceAdminUpdateActivity : ComponentActivity() {
    private val viewModel : PriceAdminDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mappingId = intent?.extras?.get("mapping_id")
        val machine = intent?.extras?.get("machine")


        setContent {
            PriceAdminUpdateCompose(viewModel = viewModel, mappingId = mappingId as Int, machine = machine as String)


        }

    }
}