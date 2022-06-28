package com.mitralaundry.xpro.ui.screen.admin.merchant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mitralaundry.xpro.data.model.response.Merchant
import com.mitralaundry.xpro.data.model.response.Status

@Composable
fun MerchantScreen(
    navHostController: NavHostController,
    viewModel: ViewModelMerchant,
    mappingDevice: String
) {
    val data by viewModel.data.observeAsState(initial = listOf())
    val error by viewModel.error.observeAsState()
    val status by viewModel.status.observeAsState(Status.NONE)
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)

    var refresh by remember {
        mutableStateOf(false)
    }

    if (status == Status.LOADING) {
        refresh = true
    } else if (status == Status.SUCCESS) {
        refresh = false
    }

    println("mappingDevice: $mappingDevice")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (mappingDevice == "map") {
                        Text("Mapping Device" , Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    } else {
                        Text(text = "Merchant" , Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    }
                },

                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },

                actions = {
                    if (mappingDevice == "map") {
                        ""
                    } else {
                        IconButton(onClick = { navHostController.navigate("merchant-add") }) {
                            Icon(Icons.Filled.Add, contentDescription = "add")
                        }
                    }
                },
                backgroundColor = color,
                contentColor = colorWhite
            )
        },
        content = {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refresh),
                onRefresh = {
                    viewModel.getAllMerchant()
                }) {
                List(navHostController, data, mappingDevice)
            }
        }
    )
}


@Composable
fun List(
    navHostController: NavHostController,
    data: List<Merchant>, mappingDevice: String
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(data) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .clickable {
                        if (mappingDevice == "map"){
                            navHostController.navigate("mapping-detail/${item.merchantId}")
                        } else {
                            navHostController.navigate("merchant-detail/${item.merchantId}")
                        }
                    }
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Merchant ID : ${item.merchantId}")
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item.name)
                        Text(text = item.address, Modifier.padding(end = 16.dp))
                    }
                }
            }
        }
    }
}
