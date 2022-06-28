package com.mitralaundry.xpro.ui.screen.merchant.kelolakasir

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAccountActivity : ComponentActivity() {
    private val viewModelUser: ViewModelUser by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val outletId = intent?.extras?.get("outlet_id")
        println("outletId saya : $outletId")
        setContent {
            val navController =
                NavHostController(this@AddAccountActivity)
            AddAccountCompose(viewModel = viewModelUser, outletId.toString(),this, navController)
        }
    }
}