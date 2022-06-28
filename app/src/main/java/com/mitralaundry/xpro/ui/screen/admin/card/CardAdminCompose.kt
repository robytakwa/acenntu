package com.mitralaundry.xpro.ui.screen.admin.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.ui.screen.ShareViewModel
import com.mitralaundry.xpro.ui.screen.merchant.topup.CardDesign
import kotlinx.coroutines.launch
import java.text.NumberFormat

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun CardAdminCompose(viewModel: ShareViewModel) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val listMerchant by viewModel.listMerchant.observeAsState(emptyList())
    var merchantId by remember {
        mutableStateOf("")
    }
    viewModel.getAllMerchant()
    BottomSheetScaffold(
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
                    text = "Manage Card",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Pilih Merchant")
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(listMerchant) { item ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    merchantId = item.name
                                    viewModel.setMerchantId(item.merchantId)
                                    viewModel.setTitle(item.name)
                                    scope.launch {
                                        scaffoldState.bottomSheetState.collapse()
                                    }
                                },
                            text = item.name
                        )
                        Divider()
                    }
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        var visible by remember {
            mutableStateOf(false)
        }
        val username by viewModel.username.observeAsState("")
        val saldo by viewModel.saldo.observeAsState(0)
        val info by viewModel.textInfo.observeAsState("")
        val openDialog by viewModel.openDialog.observeAsState(false)
        val titleDialog by viewModel.titleDialog.observeAsState("")
        val disableButton by viewModel.disableButton.observeAsState(false)
        val disableButtonNext by viewModel.disableButton.observeAsState(false)

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            AnimatedVisibility(
                visible = !visible,
                modifier = Modifier.fillMaxSize(),
                enter = slideInHorizontally(
                    initialOffsetX = { -300 }, // small slide 300px
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing // interpolator
                    )
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            ) {

                var expanded by remember { mutableStateOf(false) }
                val suggestions =
                    listOf(
                        "Master Key",
                        "Master Price",
                        "Master Reset",
                        "Master Counter",
                        "Master Mapping"
                    )
                var selectedText by remember { mutableStateOf("") }

                var dropDownWidth by remember { mutableStateOf(0) }
                var dropDownHeigh by remember { mutableStateOf(0) }
                val icon = if (expanded)
                    painterResource(id = R.drawable.ic_baseline_expand_less_24) //it requires androidx.compose.material:material-icons-extended
                else
                    painterResource(id = R.drawable.ic_baseline_expand_more_24)

                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        OutlinedTextField(
                            value = merchantId,
                            readOnly = true,
                            onValueChange = {  },
                            modifier = Modifier
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        if (scaffoldState.bottomSheetState.isCollapsed) {
                                            scaffoldState.bottomSheetState.expand()
                                        } else {
                                            scaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                                },
                            enabled = false,
                            label = { Text("Pilih Merchant") },
                        )
                        OutlinedTextField(
                            value = selectedText,
                            readOnly = true,
                            onValueChange = {},
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                                .clickable { expanded = !expanded }
                                .onSizeChanged {
                                    dropDownWidth = it.width
                                    dropDownHeigh = it.height
                                },
                            label = { Text(stringResource(R.string.pilih_tipe_kartu)) },
                            trailingIcon = {
                                Icon(painter = icon, "contentDescription",
                                    Modifier.clickable { expanded = !expanded })
                            },
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { dropDownWidth.toDp() }),
                            offset = DpOffset(
                                x = 16.dp,
                                y = 3.dp
                            )
                        ) {
                            suggestions.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    expanded = false
                                    selectedText = label
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                        when (selectedText) {
                            suggestions[0] -> {
                                viewModel.setMasterKey(MasterType.MASTER)
                                viewModel.setUserName("Master Key")
                                MasterCardCompose(viewModel = viewModel, topPadding = dropDownHeigh)
                            }
                            suggestions[1] -> {
                                viewModel.setMasterKey(MasterType.PRICE)
                                viewModel.setUserName("Master Price")
                                MasterPriceCompose(
                                    viewModel = viewModel,
                                    topPadding = dropDownHeigh
                                )
                            }
                            suggestions[2] -> {
                                viewModel.setMasterKey(MasterType.RESET)
                                viewModel.setUserName("Master Reset")
                            }
                            suggestions[3] -> {
                                viewModel.setMasterKey(MasterType.COUNTER)
                                viewModel.setUserName("Master Counter")
                            }
                            suggestions[4] -> {
                                viewModel.setMasterKey(MasterType.MAC_ADDRESS)
                                viewModel.setUserName("Master Mapping")
                            }
                        }
                    }

                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        enabled = disableButtonNext,
                        shape = RoundedCornerShape(percent = 50),
                        onClick = {
                            visible = if (!visible) !visible else true

                        }) {
                        Text(text = "Selanjutnya")
                    }
                }
            }

            AnimatedVisibility(
                visible = visible,
                modifier = Modifier.fillMaxSize(),
                enter = slideInHorizontally(
                    initialOffsetX = { it }, // small slide 300px
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing // interpolator
                    )
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CardDesign(username = username, saldo = saldo, visibleSaldo = false)

                    if (openDialog) {
                        AlertDialog(onDismissRequest = { },//viewModel.setOpenDialog(false) },
                            title = {
                                Text(text = titleDialog)
                            },
                            text = {
                                Text(text = info)
                            },
                            confirmButton = {
                                TextButton(onClick = { viewModel.setOpenDialog(false) }) {
                                    Text(text = "Oke")
                                }
                            }
                        )
                    }

                    Button(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = {
                            viewModel.writeMasterCard()
                        },
                        enabled = disableButton
                    ) {
                        Text(text = "Proses")
                    }
                }
            }
        }
    }
}

@Composable
fun MasterPriceCompose(viewModel: ShareViewModel, topPadding: Int) {
    viewModel.setUserName("Master Price")
    val paddingTop = with(LocalDensity.current) { topPadding.toDp() }
    Column(
        modifier = Modifier.padding(start = 16.dp, top = paddingTop, 16.dp)
    ) {
        val price by viewModel.price.observeAsState("")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = if (price.isBlank()) price else
                NumberFormat.getInstance()
                    .format(price.filter { it.isDigit() }.toInt()),
            onValueChange = { viewModel.setPrice(it) },
            label = {
                Text(text = "Price")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@Composable
fun MasterCardCompose(topPadding: Int, viewModel: ShareViewModel) {
    viewModel.setUserName("Master Key")
    val title by viewModel.title.observeAsState("")
    val pulse by viewModel.pulse.observeAsState(false)
    val price by viewModel.price.observeAsState("")
    val brand by viewModel.brand.observeAsState("")
    val typeMachine by viewModel.brand.observeAsState("")
    val duration by viewModel.duration.observeAsState("")
    val onOff = if (pulse) "Pulse ON" else "Pulse OFF"
    val paddingTop = with(LocalDensity.current) { topPadding.toDp() }
    val suggestions =
        listOf("Mesin Pengering", "Mesin Cuci")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    val icon = if (expanded)
        painterResource(id = R.drawable.ic_baseline_expand_less_24) //it requires androidx.compose.material:material-icons-extended
    else
        painterResource(id = R.drawable.ic_baseline_expand_more_24)

    var dropDownWidth by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end =  16.dp)
            .onSizeChanged {
                dropDownWidth = it.width
            }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = title,
            onValueChange = { viewModel.setTitle(it) },
            label = {
                Text(text = "Title")
            }
        )
        Column {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = selectedText,
                onValueChange = {},
                label = {
                    Text(text = "Type Machine")
                },
                trailingIcon = {
                    Icon(painter = icon, "contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { dropDownWidth.toDp() })
                    .fillMaxWidth()
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            selectedText = label
                        }) {
                        Text(text = label)
                    }
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = if (price.isBlank()) price else
                NumberFormat.getInstance()
                    .format(price.filter { it.isDigit() }.toInt()),
            onValueChange = { viewModel.setPrice(it) },
            label = {
                Text(text = "Price")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = brand,
            onValueChange = { viewModel.setBrand(it) },
            label = {
                Text(text = "Brand")
            },
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = duration,
            onValueChange = { viewModel.setDuration(it) },
            label = {
                Text(text = "Duration (ms)")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Row() {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(3f),
                text = onOff,
            )
            Switch(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                checked = pulse,
                onCheckedChange = { viewModel.setPulse(it) },

                )
        }
        when (selectedText) {
            suggestions[0] -> {
                viewModel.setTypeMachine(suggestions[0])
            }
            else -> {
                viewModel.setTypeMachine(suggestions[1])
            }
        }
    }
}