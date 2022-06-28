package com.mitralaundry.xpro.ui.screen.admin.merchant.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mitralaundry.xpro.data.model.response.Status

@Composable
fun MerchantAddScreen(
    navHost: NavHostController,
    viewModel: ViewModelAddMerchant
) {
    val status by viewModel.status.observeAsState(Status.NONE)
    val message by viewModel.errorMessage.observeAsState("")
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text("Add Merchant" , Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            },
            navigationIcon = {
                IconButton(onClick = { navHost.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "back")
                }
            },

            backgroundColor = color,
            contentColor = colorWhite
        )
    }, content = {
        Box {
            if (status == Status.LOADING) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            if (status == Status.SUCCESS) {
                AlertDialog(onDismissRequest = { },
                    title = {
                        Text(text = "Success")
                    },
                    text = {
                        Text(text = message)
                    },
                    confirmButton = {
                        TextButton(onClick = { navHost.popBackStack() }) {
                            Text(text = "Oke")
                        }
                    }
                )
            }

            LazyColumn {
                item {
                    NameMerchant(viewModel)
                    NameOutlet(viewModel)
                    NamePic(viewModel)
                }
            }
        }
    })
}

@Composable
fun NameMerchant(viewModel: ViewModelAddMerchant) {
    val merchantName by viewModel.merchantName.observeAsState("")
    val merchantPhone by viewModel.merchantPhone.observeAsState("")
    val merchantAddress by viewModel.merchantAddress.observeAsState("")
    val merchantDescription by viewModel.merchantDescription.observeAsState("")
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = merchantName,
            onValueChange = { viewModel.setMerchanName(it) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = merchantPhone,
            onValueChange = { viewModel.setMerchanPhone(it) },
            label = { Text("Telepon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = merchantAddress,
            onValueChange = { viewModel.setMerchanAddress(it) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = merchantDescription,
            onValueChange = { viewModel.setMerchanDescription(it) },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 5
        )
    }
}

@Composable
fun NameOutlet(viewModel: ViewModelAddMerchant) {
    val outletName by viewModel.outletName.observeAsState("")
    val outletPhone by viewModel.outletPhone.observeAsState("")
    val outletAddress by viewModel.outletAddress.observeAsState("")
    val outletDescription by viewModel.outletDescription.observeAsState("")
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Outlet", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        OutlinedTextField(
            value = outletName,
            onValueChange = { viewModel.setOutletName(it) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = outletPhone,
            onValueChange = { viewModel.setOutletPhone(it) },
            label = { Text("Telepon") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = outletAddress,
            onValueChange = { viewModel.setOutletAddress(it) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = outletDescription,
            onValueChange = { viewModel.setOutletDescription(it) },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 5
        )
    }
}

@Composable
fun NamePic(viewModel: ViewModelAddMerchant) {
    val picName by viewModel.picName.observeAsState("")
    val picPhone by viewModel.picPhone.observeAsState("")
    val picEmail by viewModel.picEmail.observeAsState("")
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Pic", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        OutlinedTextField(
            value = picName,
            onValueChange = { viewModel.setPicName(it) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = picEmail,
            onValueChange = { viewModel.setPicEmail(it) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = picPhone,
            onValueChange = { viewModel.setPicPhone(it) },
            label = { Text("Phone") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = color,
                    contentColor = colorWhite

                ),
                onClick = {
                viewModel.saveMerchant()
            }, modifier = Modifier.padding(top = 16.dp)) {
                Text(text = "Save")
            }
        }
    }
}