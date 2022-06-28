package com.mitralaundry.xpro.ui.screen.merchant.member.detail

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.ui.screen.ShareViewModel
import com.mitralaundry.xpro.ui.screen.merchant.member.add.PrefixTransformation
import com.mitralaundry.xpro.ui.screen.merchant.topup.TopUpActivity

@ExperimentalAnimationApi
@Composable
fun DetailMemberCompose(viewModel: ShareViewModel) {

    viewModel.getDetail()

    val context = LocalContext.current
    val username by viewModel.username.observeAsState("")
    val phoneNumber by viewModel.phoneNumber.observeAsState("")
    val saldo by viewModel.saldo.observeAsState(0)
    val status by viewModel.status.observeAsState(Status.NONE)
    val isErrorNama = false
    val isErrorPhoneNumber = false
    val isErrorSaldo = false
    val errorName = ""
    val errorPhone = ""
    val errorSaldo = ""
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)
    val context2 = ( LocalContext.current as Activity)
    var editable by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Detail Member", Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },

                navigationIcon = {
                    IconButton(onClick = {
                        context2.finish()


                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back", tint = colorWhite)
                    }
                },
                backgroundColor = color,
                contentColor = colorWhite


            )
        },
    ) {
        if (status == Status.LOADING) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceAround) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = color,
                        contentColor = colorWhite
                    ),
                    onClick = {
                    val intent = Intent(context, TopUpActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Text(text = "Topup Saldo")
                }
            }


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                value = username,
                onValueChange = { viewModel.setUserName(it) },
                label = {
                    Text(text = "User name")
                },
                trailingIcon = {
                    if (isErrorNama)
                        Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                },
                placeholder = {
                    Text(text = "User name")
                },
                enabled = editable,
                maxLines = 1
            )
            if (isErrorNama) {
                Text(
                    text = errorName,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phoneNumber,
                onValueChange = { viewModel.setPhoneNumber(it) },
                label = {
                    Text(text = "Phone number")
                },
                trailingIcon = {
                    if (isErrorPhoneNumber)
                        Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                },
                placeholder = {
                    Text(text = "Phone number")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                enabled = editable,
                visualTransformation = PrefixTransformation("(+62) "),
                maxLines = 1
            )
            if (isErrorPhoneNumber) {
                Text(
                    text = errorPhone,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = saldo.toString(),
                onValueChange = { viewModel.setSaldo(it.toInt()) },
                label = {
                    Text(text = "Saldo")
                },
                placeholder = {
                    Text(text = "Default")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                trailingIcon = {
                    if (isErrorSaldo)
                        Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                },
                enabled = editable,
                visualTransformation = PrefixTransformation("Rp "),
                maxLines = 1
            )
            if (isErrorSaldo) {
                Text(
                    text = errorSaldo,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = color,
                    contentColor = colorWhite

                ),
                onClick = {
                editable = !editable
                if (!editable) {
                    viewModel.updateMember()
                }
            }) {
                Text(text = if (editable) "Update" else "Ubah")
            }

        }
    }
}