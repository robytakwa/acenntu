package com.mitralaundry.xpro.ui.screen.admin.merchant.detail

import android.content.Intent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mitralaundry.xpro.data.model.OutletOnClick
import com.mitralaundry.xpro.data.model.response.Merchant
import com.mitralaundry.xpro.data.model.response.Outlet
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.ui.screen.admin.mapping.MappingActivity

@Composable
fun MerchantDetail(
    navHostController: NavHostController,
    viewModel: ViewModelMerchantDetail,
    merchantId: String?, mappingDevice: String?
) {
    viewModel.getMerchantDetail(merchantId)
    val data by viewModel.data.observeAsState(null)
    val status by viewModel.status.observeAsState()
    val color = Color(0xFF207CB9)
    val colorWhite = Color(0xFFFCFDFD)
    val context = LocalContext.current

    println("map saya : $mappingDevice")

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = color,
                contentColor = colorWhite,

                title = {
                    if (mappingDevice == "map") {
                        Text(
                            text = "Mapping Device",
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "Merchant List",
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
            )
        },
        content = {
            when (status) {
                Status.SUCCESS -> {
                    DetailMenu(
                        onClickAddOutlet = {
                            navHostController.navigate("outlet-add/${merchantId}")
                        },
                        onOutletClicked = { outletClicked ->
                            if (mappingDevice == "map") {
//                                navHostController.navigate("mapping-list/${outletId}")
                                val intent = Intent(context, MappingActivity::class.java)
                                intent.putExtra("outletId", outletClicked.outletId)
                                intent.putExtra("outletName", outletClicked.outletName)
                                context.startActivity(intent)
                            } else {
                                navHostController.navigate("outlet-update/${outletClicked.outletId}")
                            }
                        },
                        data!!,
                        mappingDevice
                    )
                }
                Status.LOADING -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun DetailMenu(
    onClickAddOutlet: () -> Unit,
    onOutletClicked: (OutletOnClick) -> Unit,
    data: Merchant, mappingDevice: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (mappingDevice == "map") {
            ""
        } else {

            Row() {
                Text(modifier = Modifier.width(100.dp), text = "ID")
                Text(text = data.merchantId)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row() {
                Text(modifier = Modifier.width(100.dp), text = "Nama")
                Text(text = data.name)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row() {
                Text(modifier = Modifier.width(100.dp), text = "Alamat")
                Text(text = data.address)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row() {
                Text(modifier = Modifier.width(100.dp), text = "Telepon ")
                Text(text = data.phone)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row() {
                Text(modifier = Modifier.width(100.dp), text = "Deskripsi")
                data.description?.let { Text(text = it) }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            Text(
                text = "Outlet List",
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .weight(10f), textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                onClickAddOutlet()
            }) {
                if (mappingDevice == "map") {
                    ""
                } else {

                    Icon(Icons.Default.Add, contentDescription = "", Modifier.weight(1f))
                }
            }
        }
        ListOutlets(data.outlets, onOutletClicked = { onOutletClicked(it) })
    }
}


@Composable
fun ListOutlets(data: MutableList<Outlet>, onOutletClicked: (OutletOnClick) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(data) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            ) {
                Column(modifier = Modifier
                    .padding(8.dp)
                    .clickable { onOutletClicked(OutletOnClick(item.outletId, item.name)) }) {
                    Text(text = "Outlet ID : ${item.outletId}")
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(text =  "${item.name} - ${item.address}")
//                        Text(text = item.name "-" item.address)
                        //create
//                        Text(text = item.address, Modifier.padding(end = 16.dp))
                    }
                }
            }
        }
    }
}