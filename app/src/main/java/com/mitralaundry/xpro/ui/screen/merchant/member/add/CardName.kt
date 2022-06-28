package com.mitralaundry.xpro.ui.screen.merchant.member.add

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.ui.screen.ShareViewModel
import com.mitralaundry.xpro.ui.screen.merchant.topup.CardDesign

@ExperimentalAnimationApi
@Composable
fun CardName(viewModel: ShareViewModel) {
    val username by viewModel.username.observeAsState()
    val saldo by viewModel.saldo.observeAsState()
    val enableButtonPress by viewModel.disableButton.observeAsState()
    val textInfo by viewModel.textInfo.observeAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.primary
                    )
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = stringResource(R.string.image_des_back_arrow),
                        tint = Color.White
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "New User",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardDesign(username = username!!, saldo = saldo!!)
            Text(
                modifier = Modifier.padding(10.dp),
                text = textInfo!!,
                fontSize = 20.sp
            )
            Button(
                onClick = {
                    viewModel.setIsNewCard(true)
                    viewModel.saveMemberToCloud()
                },
                enabled = enableButtonPress!!
            ) {
                Text(text = "Proses", fontSize = 25.sp)
            }
        }
    }
}