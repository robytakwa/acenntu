package com.mitralaundry.xpro.ui.screen.admin.merchant.outlet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
fun OutletScreen(
    navController: NavHostController,
    viewModelOutlet: ViewModelOutlet,
    merchanId: String?,
    outletId: String?
) {
    outletId?.let {
        LaunchedEffect(key1 = it) {
            viewModelOutlet.getDetailOutlet(it)
        }
    }
    val name by viewModelOutlet.name.observeAsState("")
    val phone by viewModelOutlet.phone.observeAsState("")
    val address by viewModelOutlet.address.observeAsState("")
    val description by viewModelOutlet.description.observeAsState("")
    val status by viewModelOutlet.status.observeAsState(Status.NONE)
    val message by viewModelOutlet.message.observeAsState("")
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)


    Scaffold(topBar = {
        TopAppBar(
            title = {
                if (outletId != null) {
                    Text(
                        text = "Update Outlet",
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(text = "New Outlet", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                }

            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
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
                AlertDialog(onDismissRequest = { },//viewModel.setOpenDialog(false) },
                    title = {
                        Text(text = "Success")
                    },
                    text = {
                        Text(text = message)
                    },
                    confirmButton = {
                        TextButton(onClick = { navController.popBackStack() }) {
                            Text(text = "Oke")
                        }
                    }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (status == Status.ERROR) {
                    Text(text = message)
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModelOutlet.setName(it) },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { viewModelOutlet.setPhone(it) },
                    label = { Text("Telepon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { viewModelOutlet.setAddress(it) },
                    label = { Text("Alamat") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { viewModelOutlet.setDescription(it) },
                    label = { Text("Deskripsi") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = color,
                        contentColor = colorWhite

                    ),
                    onClick = {
                        if (outletId != null) {
                            viewModelOutlet.updateOutlet(outletId)
                        } else {
                            viewModelOutlet.saveOutlet(merchanId!!)
                        }
                    }) {
                    Text(text = if (outletId != null) "Update" else "Simpan")
                }
            }
        }
    })
}