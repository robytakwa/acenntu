package com.mitralaundry.xpro.ui.screen.admin.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavHostController
import com.mitralaundry.xpro.MainActivity
import com.mitralaundry.xpro.data.model.response.Status

@Composable
    fun CreateAccountCompose(viewModel: AccountAdminViewModel, context : Context, navHostController : NavHostController) {

    val name by viewModel.name.observeAsState("")

    val email by viewModel.email.observeAsState("")
    val phone by viewModel.phone.observeAsState("")
    val isErrorNama by viewModel.isErrorName.observeAsState(false)
    val isErrorPhoneNumber by viewModel.isErrorPhoneNumber.observeAsState(false)
    val isErrorEmail by viewModel.isErrorEmail.observeAsState(false)
    val errorEmail by viewModel.errorEmail.observeAsState("")
    val errorName by viewModel.errorName.observeAsState("")
    val errorPhone by viewModel.errorPhoneNumber.observeAsState("")
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)
    val context = ( LocalContext.current as Activity)


    Surface {

        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Create Account", Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },

                navigationIcon = {
                        IconButton(onClick = {
                            context.finish()


                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "back", tint = colorWhite)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    value = name,
                    onValueChange = { viewModel.setName(it) },
                    label = {
                        Text(text = "Nama")
                    },
                    trailingIcon = {
                        if (isErrorNama)
                            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                    },
                    placeholder = {
                        Text(text = "User name")
                    },
                    maxLines = 1
                )
                if (isErrorNama) {
                    Text(
                        text = errorName,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = phone,
                    onValueChange = { viewModel.setPhoneNumber(it) },
                    label = {
                        Text(text = "Phone number")
                    },
                    trailingIcon = {
                        if (isErrorPhoneNumber)
                            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                    },
                    placeholder = {
                        Text(text = "Telepon")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    maxLines = 1
                )
                if (isErrorPhoneNumber) {
                    Text(
                        text = errorPhone,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { viewModel.setEmail(it) },
                    label = {
                        Text(text = "Email")
                    },
                    trailingIcon = {
                        if (isErrorEmail)
                            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                    },
                    placeholder = {
                        Text(text = "Email")
                    },

                    maxLines = 1
                )


                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = color,
                        contentColor = colorWhite

                        ),
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        viewModel.createAccount(context)
                    }) {
                    Text(text = "Simpan")
                }


            }
        }
    }
    dialogSucces(viewModel)
}


@Composable
private fun dialogSucces(viewModel: AccountAdminViewModel){
    val status by viewModel.statusCreate.observeAsState(Status.NONE)
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
//                //how to stop loading in circular progress indicator ?   https://stackoverflow.com/questions/56907831/how-to-stop-circular-progress-indicator-in-compose
//            }
//        }

        if (status == Status.SUCCESS) {
            AlertDialog(onDismissRequest = { },

                text = {
                    Text(text = "Data berhasil disimpan")
                },
                confirmButton = {
                    TextButton(onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)

                    }) {
                        Text(text = "Ok")
                    }
                }
            )
        }
    }
}




