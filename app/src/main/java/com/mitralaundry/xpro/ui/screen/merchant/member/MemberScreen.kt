package com.mitralaundry.xpro.ui.screen.merchant.member

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.data.model.response.ResultMember
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.ui.screen.merchant.member.detail.DetailMemberActivity
import com.mitralaundry.xpro.ui.screen.merchant.topup.TopUpActivity
import com.mitralaundry.xpro.util.Constant

@ExperimentalFoundationApi
@Composable
fun MemberScreen(
    navHostController: NavHostController,
    viewModel: ViewModelMember,
    outletId: String?,
    outletName: String?
) {
    outletId?.let {
        viewModel.setOutletId(it)
    }
    LaunchedEffect("startup") {
        viewModel.getListMember()
    }
    val context = LocalContext.current

    val data by viewModel.data.observeAsState(emptyList())
    val merchantId by viewModel.merchantId.observeAsState("")
    var refresh by remember {
        mutableStateOf(true)
    }
    val status by viewModel.status.observeAsState(Status.NONE)
    refresh = when (status) {
        Status.LOADING -> true
        Status.SUCCESS -> false
        Status.ERROR -> false
        else -> false
    }
    val isRefresh = rememberSwipeRefreshState(isRefreshing = refresh)
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)
    
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = color,
                contentColor = colorWhite,
                title = {
                Text(text = "Member", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // TODO merchant ID seharusnha tidak hardcode
                        navHostController.navigate("new-card/$outletId/$merchantId")
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "add")
                    }
                }
            )
        },
        content = {
            SwipeRefresh(
                modifier = Modifier.fillMaxSize(),
                state = isRefresh,
                onRefresh = {
                    viewModel.getListMember()
                }) {
                ListMember(data, onItemClick = {
//                    navHostController.navigate("detail-member/${it.memberId}")
                    val intent = Intent(context, DetailMemberActivity::class.java)
                    intent.putExtra("idMember", it.memberId)
                    context.startActivity(intent)
                }, outletId, outletName)
            }
        }
    )
}

@ExperimentalFoundationApi
@Composable
fun ListMember(
    data: List<ResultMember>,
    onItemClick: (ResultMember) -> Unit,
    outletId: String?,
    outletName: String?
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        stickyHeader {
            Card() {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.weight(3f)
                    ) {
                        Row() {
                            Text(modifier = Modifier.weight(1f), text = "Outlet Id ")
                            Text(modifier = Modifier.weight(3f), text = ": ${outletId!!}")
                        }
                        Row() {
                            Text(modifier = Modifier.weight(1f), text = "Name")
                            Text(modifier = Modifier.weight(3f), text = ": ${outletName}")
                        }
                    }
                }
            }
        }
        items(data) { item ->
            Row(modifier = Modifier.clickable {
                onItemClick(item)
            }) {
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                        .weight(3f)
                ) {
                    Text(text = item.memberId)
                    Text(text = item.name)
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Topup")
                        IconButton(onClick = {
                            val intent = Intent(context, TopUpActivity::class.java)
                            context.startActivity(intent)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                                contentDescription = "arrow_topup"
                            )
                        }
                    }
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Constant.HexToJetpackColor.getColor("c0c0c0"),
                thickness = 1.dp
            )
        }
    }
}
