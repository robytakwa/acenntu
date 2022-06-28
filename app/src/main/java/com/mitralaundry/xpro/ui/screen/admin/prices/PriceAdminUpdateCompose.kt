package com.mitralaundry.xpro.ui.screen.admin.prices

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mitralaundry.xpro.data.model.response.Status

@Composable
fun PriceAdminUpdateCompose (viewModel: PriceAdminDetailViewModel, mappingId : Int, machine: String) {
    val price by viewModel.price.observeAsState("")

    val isErrorPrice by viewModel.isErrorName.observeAsState(false)
    val errorName by viewModel.errorName.observeAsState("")
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)
    val context = ( LocalContext.current as Activity)




    Surface {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Update Price", Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                    navigationIcon = {
                        IconButton(onClick = {
                            context.finish()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                        }
                    },

                    backgroundColor = color,
                    contentColor = colorWhite

                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    value = price,
                    onValueChange = { viewModel.setPrice(it) },
                    label = {
                        Text(text = "Pricing")
                    },
                    trailingIcon = {
                        if (isErrorPrice)
                            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                    },
                    placeholder = {
                        Text(text = "Pricing")
                    },
                    maxLines = 1
                )
                if (isErrorPrice) {
                    Text(
                        text = errorName,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = color,
                        contentColor = colorWhite

                    ),
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        viewModel.updatePriceAdmin(mappingId)


                    }) {
                    Text(text = "Ubah")
                }

                dialogSucces(viewModel, machine,price)
            }
        }


    }
}


@Composable
private fun dialogSucces(viewModel: PriceAdminDetailViewModel, machine : String, price : String) {
    val status by viewModel.statusPriceUpdate.observeAsState(Status.NONE)
    val context = LocalContext.current


    Box {

//        if (status == Status.LOADING) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                CircularProgressIndicator()
//            }
//        }

        if (status == Status.SUCCESS) {
            AlertDialog(onDismissRequest = { },

                text = {
                    Text(text = "Berhasil Dirubah")
                },
                confirmButton = {
                    TextButton(onClick = {
                        val intent = Intent(context, TapCardPriceAdminActivity::class.java)

                        intent.putExtra("machine", machine)
                        intent.putExtra("price", price)
                        context.startActivity(intent)


                    }) {
                        Text(text = "Ok")
                    }
                }
            )
        }
    }
}

