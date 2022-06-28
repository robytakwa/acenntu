package com.mitralaundry.xpro.ui.screen.home

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.data.model.response.ItemOutlet
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.ui.screen.admin.account.AccountListAdminActivity
import com.mitralaundry.xpro.ui.screen.admin.card.CardAdminActivity
import com.mitralaundry.xpro.ui.screen.admin.prices.PriceAdminActiviy
import com.mitralaundry.xpro.ui.screen.counter.CounterActivity
import com.mitralaundry.xpro.ui.screen.merchant.kelolakasir.AccountListActivity
import com.mitralaundry.xpro.ui.screen.merchant.laporanmesin.ReportMachineActivity
import com.mitralaundry.xpro.ui.screen.merchant.laporantopup.ReportTopUpActivity
import com.mitralaundry.xpro.ui.screen.merchant.listharga.PriceListActivity
import com.mitralaundry.xpro.ui.screen.merchant.nitip.NitipActivity
import com.mitralaundry.xpro.ui.screen.merchant.profilemerchant.ProfileActivity
import com.mitralaundry.xpro.ui.screen.merchant.topup.TopUpActivity

@ExperimentalFoundationApi

@Composable
fun HomeScreen(navController: NavController, viewModelHome: ViewModelHome) {
    val context = LocalContext.current
    val user = viewModelHome.user.observeAsState()
    val data by viewModelHome.outlet.observeAsState(emptyList())
    val outletId by viewModelHome.outletId.observeAsState()
    val outletName by viewModelHome.outletName.observeAsState()
    val logout by viewModelHome.logout.observeAsState(false)
    var role =""
    var expandedUser by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(logout) {
        if (logout) {
            navController.navigate("login")
        }
    }

    Surface() {
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(86.dp)
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(4f),
                    text = "Hai, ${user.value?.userName}",
                    fontSize = 25.sp
                )
                Column() {
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = { expandedUser = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_person_24),
                            contentDescription = "person"
                        )
                    }
                    DropdownMenu(
                        expanded = expandedUser,
                        onDismissRequest = { expandedUser = false },
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                modifier = Modifier.clickable {
//                                    navController.navigate("profile")

                                    val intent = Intent(context, ProfileActivity::class.java)
                                    intent.putExtra("role", user.value?.role!!)
                                    context.startActivity(intent)
                                },
                                text = "Profile"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                modifier = Modifier.clickable {
                                    expandedUser = false
                                    viewModelHome.logout()
                                },
                                text = "Logout"
                            )
                        }
                    }
                }
            }

            MenuHome(
                viewModelHome = viewModelHome,
                data,
                user.value?.role!!,
                onCardClickNitip = {
                    val intent = Intent(context, NitipActivity::class.java)
                    context.startActivity(intent)
                },
                onCardClickSetup = {
                    navController.navigate("new-card") {
                        popUpTo("home")
                    }
                },
                onCardClickTopUp = {
                    val intent = Intent(context, TopUpActivity::class.java)
                    context.startActivity(intent)
                },
                onCardClickManageCard = {
                    val intent = Intent(context, CardAdminActivity::class.java)
                    context.startActivity(intent)
                },
                onCardClickCounter = {
                    val intent = Intent(context, CounterActivity::class.java)
                    context.startActivity(intent)
                },

                onCardClickKelolaKasir = {
                    val intent = Intent(context, AccountListActivity::class.java)
                    intent.putExtra("outlet_id", outletId)
                    context.startActivity(intent)
                },

                //TODO : error disini pas mau masuk ke menu merchant ataupun menu lainnya. kecuali menu device aman
                onCardClickMerchant = {
                    navController.navigate("merchant")
                },


                onCardClickDevice = {
                    navController.navigate("device")
                },
                onCardClickMapping = {
                    navController.navigate("mapping")
                },
                onClickMember = {
                    navController.navigate("member/$outletId/$outletName")
                },
                onCardClickLaporanLaundry = {
                    navController.navigate("report-nitip/$outletId/$outletName")
                },
                onCardClickLaporanTopup = {
//                    navController.navigate("laporan-topup/$outletId/$outletName")
                    val intent = Intent(context, ReportTopUpActivity::class.java)
                    intent.putExtra("outletId", outletId)
                    intent.putExtra("outletName", outletName)
                    context.startActivity(intent)
                },
                onCardClickLaporanMesin = {
                    val intent = Intent(context, ReportMachineActivity::class.java)
                    intent.putExtra("outletId", outletId)
                    intent.putExtra("outletName", outletName)
                    context.startActivity(intent)
                },
                onClickPriceHistory = {
                    val intent = Intent(context, PriceAdminActiviy::class.java)
                    context.startActivity(intent)
                },
                onClickListHarga = {
                    val intent = Intent(context, PriceListActivity::class.java)
                    intent.putExtra("outletId", outletId)
                    intent.putExtra("outletName", outletName)
                    context.startActivity(intent)
                },
                onClickUserAccount = {
                    val intent = Intent(context, AccountListAdminActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}


class Menu(val title: String, val icon: Painter)

@ExperimentalFoundationApi
@Composable
fun MenuHome(
    viewModelHome: ViewModelHome,
    data: List<ItemOutlet>,
    role: String,
    onCardClickManageCard: () -> Unit,
    onCardClickSetup: () -> Unit,
    onCardClickTopUp: () -> Unit,
    onCardClickNitip: () -> Unit,
    onCardClickCounter: () -> Unit,
    onCardClickMerchant: () -> Unit,
    onCardClickDevice: () -> Unit,
    onCardClickMapping: () -> Unit,
    onCardClickLaporanLaundry: () -> Unit,
    onClickMember: () -> Unit,
    onCardClickLaporanTopup: () -> Unit,
    onCardClickLaporanMesin: () -> Unit,
    onCardClickKelolaKasir: () -> Unit,
    onClickPriceHistory: () -> Unit,
    onClickListHarga: () -> Unit,
    onClickUserAccount: () -> Unit,

    ) {
    val status by viewModelHome.status.observeAsState(Status.NONE)
    val menu = mutableListOf<Menu>()

    if (role == "kasir") {
        menu.add(
            Menu(
                "Member",
                painterResource(id = R.drawable.ic_member)
            )
        )
        menu.add(
            Menu(
                "Titip Laundry",
                painterResource(id = R.drawable.ic_titip_laundry)
            )
        )

        menu.add(
            Menu(
                "Laporan Mesin",
                painterResource(id = R.drawable.ic_laporan_mesin)
            )
        )
    }
    if (role == "merchant") {
        menu.add(Menu("Member", painterResource(id = R.drawable.ic_member)))
        menu.add(
            Menu(
                "Titip Laundry",
                painterResource(id = R.drawable.ic_titip_laundry)
            )
        )


        menu.add(
            Menu(
                "List Harga",
                painterResource(id = R.drawable.ic_list_harga)
            )
        )
    }

    if (role == "merchant") {
        menu.add(
            Menu(
                "Laporan Topup",
                painterResource(id = R.drawable.ic_topup_report)
            )
        )
        menu.add(
            Menu(
                "Lp Titip Laundry",
                painterResource(id = R.drawable.ic_report_laundry)
            )
        )
        menu.add(
            Menu(
                "Kelola Kasir",
                painterResource(id = R.drawable.ic_kelola_kasir)
            )
        )

        menu.add(
            Menu(
                "Laporan Mesin",
                painterResource(id = R.drawable.ic_laporan_mesin)
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 24.dp,
                end = 16.dp
            ),
    ) {
        if (role == "administrator") {
            Card {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {

                    Text(text = "Admin")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        CardMenu(
                            "User Account",
                            onClickUserAccount,
                            painterResource(id = R.drawable.ic_user)
                        )
                        CardMenu(
                            "Merchant",
                            onCardClickMerchant,
                            painterResource(id = R.drawable.ic_merchant)
                        )
                        CardMenu(
                            "Devices",
                            onCardClickDevice,
                            painterResource(id = R.drawable.ic_device)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        CardMenu(
                            "Mapping",
                            onCardClickMapping,
                            painterResource(id = R.drawable.ic_mapping)
                        )
                        CardMenu(
                            "Price History",
                            onClickPriceHistory,
                            painterResource(id = R.drawable.ic_price_history)
                        )
                    }
                }
            }
        }

        var expanded by remember {
            mutableStateOf(false)
        }
        var dropDownWidth by remember { mutableStateOf(0) }
        val outletId by viewModelHome.outletId.observeAsState("")
        val outletName by viewModelHome.outletName.observeAsState("")

        if (role == "merchant") {
            Box(contentAlignment = Alignment.Center) {

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged {
                        dropDownWidth = it.width
                    }
                    .clickable {
                        expanded = true
                    }) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.weight(3f)
                        ) {
                            Row() {
                                Text(modifier = Modifier.weight(1f), text = "Outlet Id ")
                                Text(modifier = Modifier.weight(3f), text = ": $outletId")
                            }
                            Row() {
                                Text(modifier = Modifier.weight(1f), text = "Name")
                                Text(modifier = Modifier.weight(3f), text = ": $outletName")
                            }
                        }

                        IconButton(
                            modifier = Modifier.weight(0.5f),
                            onClick = { expanded = true }) {
                            if (expanded) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_expand_less_24),
                                    contentDescription = "arrow_down"
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_expand_more_24),
                                    contentDescription = "arrow_down"
                                )
                            }
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { dropDownWidth.toDp() }),
                    ) {
                        data.forEach { item ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                viewModelHome.setOutletId(item.outletId)
                                viewModelHome.setOutletName(item.name)
                            }) {
                                Column() {
                                    Text(text = item.outletId)
                                    Text(text = item.name)
                                }
                            }
                        }
                    }
                }
                if (status == Status.LOADING) {
                    CircularProgressIndicator()
                }
            }
        }

        if (role == "kasir") {
            Box(contentAlignment = Alignment.Center) {

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged {
                        dropDownWidth = it.width
                    }
                    .clickable {
                        expanded = true
                    }) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.weight(3f)
                        ) {
                            Row() {
                                Text(modifier = Modifier.weight(1f), text = "Outlet Id ")
                                Text(modifier = Modifier.weight(3f), text = ": $outletId")
                            }
                            Row() {
                                Text(modifier = Modifier.weight(1f), text = "Name")
                                Text(modifier = Modifier.weight(3f), text = ": $outletName")
                            }
                        }

                        IconButton(
                            modifier = Modifier.weight(0.5f),
                            onClick = { expanded = true }) {
                            if (expanded) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_expand_less_24),
                                    contentDescription = "arrow_down"
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_expand_more_24),
                                    contentDescription = "arrow_down"
                                )
                            }
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { dropDownWidth.toDp() }),
                    ) {
                        data.forEach { item ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                viewModelHome.setOutletId(item.outletId)
                                viewModelHome.setOutletName(item.name)
                            }) {
                                Column() {
                                    Text(text = item.outletId)
                                    Text(text = item.name)
                                }
                            }
                        }
                    }
                }
                if (status == Status.LOADING) {
                    CircularProgressIndicator()
                }
            }
        }
        if (role == "merchant") {
            Spacer(modifier = Modifier.height(16.dp))
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    GridLayoutMenu(data = menu, onClickItem = { title ->
                        when (title) {
                            "Member" -> {
                                onClickMember()
                            }
                            "Titip Laundry" -> {
                                onCardClickNitip()
                            }

                            "List Harga" -> {
                                onClickListHarga()
                            }
                            "Kelola Kasir" -> {
                                onCardClickKelolaKasir()
                            }
                            "Top Up" -> {
                                onCardClickTopUp()
                            }
                            "Upload Laporan" -> {
                                onCardClickCounter()
                            }
                            "Lp Titip Laundry" -> {
                                onCardClickLaporanLaundry()
                            }
                            "Laporan Topup" -> {
                                onCardClickLaporanTopup()
                            }
                            "Laporan Mesin" -> {
                                onCardClickLaporanMesin()
                            }
                        }
                    })

                }
            }
        }

        if (role == "kasir") {
            Spacer(modifier = Modifier.height(16.dp))
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    GridLayoutMenu(data = menu, onClickItem = { title ->
                        when (title) {
                            "Member" -> {
                                onClickMember()
                            }
                            "Titip Laundry" -> {
                                onCardClickNitip()
                            }
                            "Laporan Mesin" -> {
                                onCardClickLaporanMesin()
                            }
                        }
                    })

                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@ExperimentalFoundationApi
@Composable
fun GridLayoutMenu(data: List<Menu>, onClickItem: (String) -> Unit) {
    LazyVerticalGrid(cells = GridCells.Fixed(3)) {
        items(data) { item ->
            Column(
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp)
                    .clickable {
                        onClickItem(item.title)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = item.icon,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "${item.title}"
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 4.dp,
                            top = 4.dp,
                            end = 8.dp
                        ),
                    text = item.title,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardMenu(
    title: String,
    onCardClick: () -> Unit,
    icon: Painter
) {
    Card(onClick = {
        onCardClick()
    }) {
        Column(
            modifier = Modifier
                .size(90.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Image(
                painter = icon,
                modifier = Modifier.size(30.dp),
                contentDescription = "topup"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 4.dp,
                        top = 4.dp,
                        end = 8.dp
                    ),

                text = title,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

