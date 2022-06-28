package com.mitralaundry.xpro.ui.screen.merchant.nitip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.ui.screen.ShareViewModel
import com.mitralaundry.xpro.ui.screen.merchant.member.add.PrefixTransformation
import com.mitralaundry.xpro.ui.screen.merchant.topup.CardDesign
import java.text.NumberFormat

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Nitip(viewModel: ShareViewModel) {
    val username by viewModel.username.observeAsState()
    val saldo by viewModel.saldo.observeAsState()
    val textInfo by viewModel.textInfo.observeAsState()
    val disableButton by viewModel.disableButton.observeAsState()
    val totalPotong by viewModel.totalPotong.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(
                        color = color
                    )
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.backButton() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = stringResource(R.string.image_des_back_arrow),
                        tint = Color.White
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Nitip Laundry",
                    textAlign = TextAlign.Center,
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
            TextField(
                value = if (totalPotong.isNullOrBlank()) totalPotong!! else
                    NumberFormat.getInstance()
                        .format(totalPotong!!.filter { it.isDigit() }.toInt()),
                onValueChange = { viewModel.setTotalPotong(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                visualTransformation = PrefixTransformation("Rp "),
                maxLines = 1,
                label = {
                    Text(text = "Total potong")
                },
                textStyle = TextStyle(
                    fontSize = 20.sp
                )
            )
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    viewModel.cutSaldo()
                },
                enabled = disableButton!!
            ) {
                Text(text = "Proses", fontSize = 25.sp)
            }
        }
    }
}