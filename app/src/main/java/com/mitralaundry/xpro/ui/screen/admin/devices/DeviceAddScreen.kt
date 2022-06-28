package com.mitralaundry.xpro.ui.screen.admin.devices

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mitralaundry.xpro.MainActivity
import com.mitralaundry.xpro.data.model.response.Status

@Composable
fun DeviceAddScreen(
    navHostController: NavHostController,
    viewModel: ViewModelDevice,
    deviceId: String?
) {
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)

    var edit by remember {
        mutableStateOf(false)
    }
    deviceId?.let {
        viewModel.getDetailDevice(it)
        edit = true
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = if (edit) "Update Device" else "New Device")
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },

                backgroundColor = color,
                contentColor = colorWhite

            )
        },
        content = {
            Device(viewModel, edit)
        }
    )
}

@Composable
fun Device(viewModel: ViewModelDevice, edit: Boolean) {
    val type by viewModel.type.observeAsState("")
    val name by viewModel.name.observeAsState("")
    val serialNumber by viewModel.serialNumber.observeAsState("")
    val mac by viewModel.mac.observeAsState("")
    val isActive by viewModel.isActive.observeAsState(false)
    val ipAddress by viewModel.address.observeAsState("")
    val types = listOf("Wash Machine", "Dry Machine", "Micon")
    val statusTypes = listOf("Aktif", "Tidak Aktif")
    var expanded by remember { mutableStateOf(false) }
    var expandedStatus by remember { mutableStateOf(false) }
    var onClickTypeIcon by remember { mutableStateOf(false) }
    var onClickTypeIconStatus by remember { mutableStateOf(false) }
    val context = ( LocalContext.current as Activity)
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)



    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = type,
                onValueChange = {},
                enabled = false,
                label = { Text("Type") },
                modifier = Modifier.fillMaxWidth(),
            )
            IconButton(
                onClick = {
                    onClickTypeIcon = true
                    expanded = true
                },
                Modifier
                    .size(60.dp)
                    .align(
                        Alignment.CenterEnd
                    )
                    .padding(end = 10.dp)
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
                if (onClickTypeIcon) {
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        types.forEach { label ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                viewModel.setType(label)
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }
            }
        }

        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.setName(it) },
            label = { Text("Nama Device") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = serialNumber,
            onValueChange = { viewModel.setSerialNumber(it) },
            label = { Text("Serial Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

            OutlinedTextField(
                value = ipAddress,
                onValueChange = { viewModel.setAddress(it) },
                label = { Text("IP Address") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

        OutlinedTextField(
            value = mac,
            onValueChange = { viewModel.setMac(it) },
            label = { Text("Mac") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 5
        )
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = "Active")
//            Switch(checked = isActive, onCheckedChange = { viewModel.setActive(it) })
//        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp), contentAlignment = Alignment.Center
        ) {
            Button(  colors = ButtonDefaults.buttonColors(
                backgroundColor = color,
                contentColor = colorWhite

            ),

                onClick = { if (edit) viewModel.updateDevice(context) else
                viewModel.saveDevice(context)

            }) {
                Text(text = if (edit) "Update"
                else "Save")
            }
        }
    }
    dialogSucces(viewModel)
}

@Composable
private fun loadData(viewModel: ViewModelDevice) {
    val status by viewModel.status.observeAsState(Status.NONE)
        if (status == Status.LOADING) {
            Box {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()

            }
        }
    }

}

//TODO : Dari sini kalau succes di direct ke HomeScreen

@Composable
private fun dialogSucces(viewModel: ViewModelDevice){
    val status by viewModel.status.observeAsState(Status.NONE)
//    val context = LocalContext.current
    val context = ( LocalContext.current as Activity)



    Box {

        if (status == Status.SUCCESS) {
            AlertDialog(onDismissRequest = { },

                text = {
                    Text(text = "Data berhasil disimpan")
                },
                confirmButton = {
                    TextButton(onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)

                    }) {
                        Text(text = "Ok")
                    }
                }
            )
        }
    }
}




