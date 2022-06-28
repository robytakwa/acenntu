package com.mitralaundry.xpro.ui.screen.merchant.listharga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePriceActivity : ComponentActivity() {
    private val viewModel: PriceListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mappingId = intent?.extras?.get("mappingId")
        val machine = intent?.extras?.get("machine")
        val date = intent?.extras?.get("date")

        setContent {
            UpdatePriceCompose(
                viewModel = viewModel,
                mappingId = mappingId as Int,
                this,
                machine = machine as String,
                date = date as String
            )
        }
    }
}