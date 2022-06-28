package com.mitralaundry.xpro.ui.screen.merchant.topup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.ui.screen.ShareViewModel
import java.text.NumberFormat

data class Harga(val title: Int, val topup: Int, val harga: Int)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListTopup(viewModel: ShareViewModel) {
    val finish by viewModel.finish.observeAsState(false)
    val listTopUp = arrayListOf<Harga>()
    listTopUp.add(Harga(5, 5000, 5000))
    listTopUp.add(Harga(10, 10000, 10000))
    listTopUp.add(Harga(20, 20000, 20000))
    listTopUp.add(Harga(50, 50000, 50000))
    listTopUp.add(Harga(100, 100000, 100000))
    listTopUp.add(Harga(150, 150000, 150000))

    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 8.dp
        )
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.daftar_harga), fontSize = 20.sp
        )
        var selectedIndex by remember {
            mutableStateOf(-1)
        }
        if (finish) {
            selectedIndex = -1
            viewModel.setFinish(false)
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 10.dp,
                top = 8.dp,
                end = 10.dp,
                bottom = 10.dp
            )
        ) {
            items(listTopUp) { items ->
                androidx.compose.material.Card(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(5.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                if (items.topup == selectedIndex)
                                    Color.LightGray else colorResource(id = R.color.background_menu)
                            )
                            .selectable(
                                selected = items.topup == selectedIndex,
                                onClick = {
                                    viewModel.selectHarga(items)
                                    if (selectedIndex != items.topup)
                                        selectedIndex = items.topup
                                }
                            ),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "${items.title}K",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = NumberFormat.getInstance().format(items.harga),
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}