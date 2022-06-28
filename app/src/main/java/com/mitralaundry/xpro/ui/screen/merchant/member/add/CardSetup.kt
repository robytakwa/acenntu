package com.mitralaundry.xpro.ui.screen.merchant.member.add

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mitralaundry.xpro.data.model.response.Status
import java.text.NumberFormat


@Composable
fun SetupNewCard(
    navHostController: NavHostController,
    viewModel: ViewModelCardSetup,
    outletId: String?,
    merchantId: String?
) {
    val context = LocalContext.current
    val username by viewModel.name.observeAsState("")
    val phoneNumber by viewModel.phoneNumber.observeAsState("")
    val saldo by viewModel.saldo.observeAsState("")
    val isErrorNama by viewModel.isErrorName.observeAsState(false)
    val isErrorPhoneNumber by viewModel.isErrorPhoneNumber.observeAsState(false)
    val isErrorSaldo by viewModel.isErrorSaldo.observeAsState(false)
    val errorSaldo by viewModel.errorSaldo.observeAsState("")
    val errorName by viewModel.errorName.observeAsState("")
    val errorPhone by viewModel.errorPhoneNumber.observeAsState("")
    val status by viewModel.status.observeAsState(Status.NONE)
    viewModel.setOutletId(outletId!!)

    val member by viewModel.member.observeAsState(null)
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)

    LaunchedEffect(status) {
        if (status == Status.SUCCESS) {
            member.let {
                navHostController.popBackStack()
                val intent = Intent(context, AddMemberActivity::class.java)
                intent.putExtra("name", member?.name)
                intent.putExtra("phone_number", member?.phone)
                intent.putExtra("saldo", member?.saldo)
                intent.putExtra("outlet_id", member?.outletId)
                intent.putExtra("merchant_id", merchantId)
                context.startActivity(intent)
                viewModel.setStatus(Status.NONE)
            }
        }
    }
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "New Member", Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                ) },
                    navigationIcon = {
                        IconButton(onClick = { navHostController.popBackStack() }) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    value = username,
                    onValueChange = { viewModel.setName(it) },
                    label = {
                        Text(text = "User name")
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
                    value = phoneNumber,
                    onValueChange = { viewModel.setPhoneNumber(it) },
                    label = {
                        Text(text = "Phone number")
                    },
                    trailingIcon = {
                        if (isErrorPhoneNumber)
                            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                    },
                    placeholder = {
                        Text(text = "Phone number")
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
                    value = if (saldo.isEmpty()) "0" else
                        NumberFormat.getInstance().format(saldo.filter { it.isDigit() }.toInt()),
                    onValueChange = { viewModel.setSaldo(it) },
                    label = {
                        Text(text = "Saldo")
                    },
                    placeholder = {
                        Text(text = "Default")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    trailingIcon = {
                        if (isErrorSaldo)
                            Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
                    },
                    visualTransformation = PrefixTransformation("Rp "),
                    maxLines = 1
                )
                if (isErrorSaldo) {
                    Text(
                        text = errorSaldo,
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
                        viewModel.proses()
                    }) {
                    Text(text = "Registrasi User")
                }
            }
        }
    }
}

class PrefixTransformation(val prefix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return PrefixFilter(text, prefix);
    }

}

fun PrefixFilter(number: AnnotatedString, prefix: String): TransformedText {

    val out = prefix + number.text
    val prefixOffset = prefix.length

    val numberOffSetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset + prefixOffset
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= prefixOffset - 1) return prefixOffset
            return offset - prefixOffset
        }
    }

    return TransformedText(AnnotatedString(out), numberOffSetTranslator)
}

