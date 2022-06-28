package com.mitralaundry.xpro.ui.screen.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.ui.screen.ShareViewModel

@Composable
fun CounterCompose(viewModel: ShareViewModel) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.primary
                    )
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.backButton() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = stringResource(R.string.image_des_back_arrow),
                        tint = Color.White
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Counter",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
        },
    ) {
        val counter by viewModel.counter.observeAsState("0")
        val totalCounter by viewModel.totalCounter.observeAsState("0")
        val typeMachine by viewModel.typeMachine.observeAsState("")
        viewModel.readCounter()
        Column() {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Tipe Mesin : $typeMachine",
                fontSize = 30.sp
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Jumlah tab $counter",
                fontSize = 30.sp
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Total Pendapatan $totalCounter",
                fontSize = 30.sp
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { viewModel.uploadCounter() }) {
                    Text(text = "Upload")
                }
            }
        }

    }
}