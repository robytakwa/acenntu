package com.mitralaundry.xpro.ui.screen.admin.account

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountActivity : ComponentActivity() {
    private val viewModel : AccountAdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navhostController =
                NavHostController(this@CreateAccountActivity)

            CreateAccountCompose(viewModel = viewModel, this, navhostController)

        }

    }
}