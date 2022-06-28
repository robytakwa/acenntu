package com.mitralaundry.xpro.ui.screen.merchant.laporantopup

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
import com.mitralaundry.xpro.data.model.ReportLaundry
import com.mitralaundry.xpro.ui.screen.merchant.reportnitip.ViewModelReportNitip

@Composable
fun LaporanTopupCompose(
    onClickBackArrow: () -> Unit,
    outletId: String,
    outletName: String,
    viewModel: ViewModelReportNitip,
) {
    val list by viewModel.listReport.observeAsState(emptyList())
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Laporan Topup") },
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
                ListTopupCompose(list)
            }
        }
    )
}

@Composable
fun ListTopupCompose(list: List<ReportLaundry>) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(list) {
            ListItemTopupCompose(item = it)
        }
    }
}

@Composable
fun ListItemTopupCompose(item: ReportLaundry) {
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
