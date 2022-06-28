package com.mitralaundry.xpro.ui.screen.admin.devices

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mitralaundry.xpro.data.model.Device
import java.util.*

@Composable
fun DeviceScreen(navHostController: NavHostController, viewModel: ViewModelDevice) {
    val data by viewModel.data.observeAsState(initial = listOf())
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Device List", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)


            },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                backgroundColor = color,
                contentColor = colorWhite,



                        actions = {
                    IconButton(onClick = { navHostController.navigate("device-add") }) {
                        Icon(Icons.Filled.Add, contentDescription = "add")
                    }
                }


            )
        },
        content = {
            DeviceList(devices = data, onClickItem = { device ->
               navHostController.navigate("device-update/${device.deviceId}")
            })
        }
    )
}


@Composable
fun DeviceList(devices: List<Device>, onClickItem: (Device) -> Unit) {
    LazyColumn {
        itemsIndexed(devices) { index, item ->
            Row(
                Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                    .clickable {
                        onClickItem(item)
                    }) {
                Column(Modifier.weight(4f)) {
                    Row() {
                        Text("Type", Modifier.weight(1f))
                        Text(": ${item.type}", modifier = Modifier.weight(2f))

                    }
                    Row() {
                        Text("Device", Modifier.weight(1f))
                        Text(": ${item.name}", modifier = Modifier.weight(2f))
                    }
                    Row() {
                        Text("SN", Modifier.weight(1f))
                        Text(": ${item.serialNumber}", modifier = Modifier.weight(2f))
                    }
                    Row() {
                        Text("Mac", Modifier.weight(1f))
                        Text(": ${item.mac}", modifier = Modifier.weight(2f))
                    }
                    Row() {
                        Text("Ip Address", Modifier.weight(1f))
                        Text(": ${item.address}", modifier = Modifier.weight(2f))
                    }
                }
                Text(text = item.status.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            }
        }
    }
}
