package com.mitralaundry.xpro.ui.screen.merchant.reportnitip

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mitralaundry.xpro.data.model.ReportLaundry

@Composable
fun ReportNitipCompose(
    navHostController: NavHostController,
    viewModel: ViewModelReportNitip,
    outletId: String,
    outletName: String
) {
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)

    val list by viewModel.listReport.observeAsState(emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = color,
                contentColor = colorWhite,
                title = {
                    Text(text = "Laporan Nitip Laundry",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,

                        )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
            )
        }
    ) {
        Column() {
            Column(Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Outlet ID", modifier = Modifier.weight(1f))
                    Text(text = ": $outletId ", modifier = Modifier.weight(3f))
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Name ", modifier = Modifier.weight(1f))
                    Text(text = ": $outletName", modifier = Modifier.weight(3f))
                }
            }
            ListNitipCompose(list)
        }
    }
}

@Composable
fun ListNitipCompose(list: List<ReportLaundry>) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(list) {
            ItemNitipLaundry(item = it)
        }
    }
}

@Composable
fun ItemNitipLaundry(item: ReportLaundry) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Member", modifier = Modifier.weight(1f))
            Text(text = ": ${item.memberId}", modifier = Modifier.weight(3f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Name", modifier = Modifier.weight(1f))
            Text(text = ": ${item.name}", modifier = Modifier.weight(3f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Topup", modifier = Modifier.weight(1f))
            Text(text = ": ${item.topup}", modifier = Modifier.weight(3f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Create date", modifier = Modifier.weight(1f))
            Text(text = ": ${item.createdDate}", modifier = Modifier.weight(3f))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}