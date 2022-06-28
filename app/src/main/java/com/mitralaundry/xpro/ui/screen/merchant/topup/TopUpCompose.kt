package com.mitralaundry.xpro.ui.screen.merchant.topup

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.ui.screen.ShareViewModel
import java.text.NumberFormat

@ExperimentalAnimationApi
@Composable

//create previewparameter for preview in compose ui tooling preview screen for topup compose class
fun TopUpCompose( viewModel: ShareViewModel) {
    val username by viewModel.username.observeAsState()
    val saldo by viewModel.saldo.observeAsState()
    val harga = viewModel.topup.observeAsState()
    val hargaFormat = NumberFormat.getInstance().format(harga.value?.harga)
    val disableButton by viewModel.disableButton.observeAsState(false)
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)
    val context = ( LocalContext.current as Activity)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Topup", Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },

                navigationIcon = {
                    IconButton(onClick = {
                        context.finish()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back", tint = colorWhite)
                    }
                },
                backgroundColor = color,
                contentColor = colorWhite
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column() {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.tab_kartu_pengguna),
                        fontSize = 25.sp
                    )
                    CardDesign(username = username!!, saldo = saldo!!.toInt())
                    ListTopup(viewModel = viewModel)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(
                            alignment = Alignment.BottomCenter
                        )
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(3f)
                    ) {
                        Text(text = stringResource(R.string.total_bayar), fontSize = 20.sp)
                        Text(
                            text = "Rp $hargaFormat",
                            fontSize = 19.sp

                        )
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        enabled = disableButton,
                        onClick = { viewModel.topUpSaldo() }) {
                        Text(text = stringResource(R.string.Bayar))
                    }
                }
            }
        },
    )
}