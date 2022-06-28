package com.mitralaundry.xpro.ui.screen.merchant.laporanmesin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitralaundry.xpro.data.model.ReportMesin

@Composable
fun LaporanMesinCompose(
    onClickBackArrow: () -> Unit,
    outletId: String,
    outletName: String,
    viewModel: ViewModelReportMesin
) {
    val list by viewModel.data.observeAsState(emptyList())
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Laporan Mesin") },
                navigationIcon = {
                    IconButton(onClick = { onClickBackArrow() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        content = {
            Column {
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
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Pilih Tanggal")
                    }
                    Text(text = "Rp. 500.000", fontSize = 20.sp)
                }
                ListMesinCompose(list = list)
            }
        }
    )
}

@Composable
fun ListMesinCompose(list: List<ReportMesin>) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(list) {
            ListItemMesinCompose(item = it)
        }
    }
}

@Composable
fun ListItemMesinCompose(item: ReportMesin) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Mechine", modifier = Modifier.weight(1f))
            Text(text = ": ${item.name}", modifier = Modifier.weight(3f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Cycle", modifier = Modifier.weight(1f))
            Text(text = ": ", modifier = Modifier.weight(3f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Price", modifier = Modifier.weight(1f))
            Text(text = ": ${item.currentPrice}", modifier = Modifier.weight(3f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Total", modifier = Modifier.weight(1f))
            Text(text = ": ${item.pendapatan}", modifier = Modifier.weight(3f))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Date", modifier = Modifier.weight(1f))
            Text(text = ": ${item.createdAt}", modifier = Modifier.weight(3f))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}