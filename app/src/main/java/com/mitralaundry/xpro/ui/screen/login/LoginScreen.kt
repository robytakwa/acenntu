package com.mitralaundry.xpro.ui.screen.login

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.ui.screen.resetpassword.ResetPasswordActivity
import com.mitralaundry.xpro.ui.theme.MEDIUM_PADDING

@ExperimentalComposeUiApi
@Composable

fun LoginScreen(
    onSuccessLogin : () -> Unit,
    viewModel: LoginViewModel,
) {
    var username by remember {
   mutableStateOf("administrator-1@pen-bot.com")

//        mutableStateOf("merchant-1@pen-bot.com")
//         mutableStateOf("kasir-1@pen-bot.com")
    }
    var password by remember {
        mutableStateOf("password1")
//        mutableStateOf("password")
    }

    val status by viewModel.status.observeAsState(Status.NONE)
    val isActive by viewModel.isActive.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val context = LocalContext.current
    val color =  Color(0xFF207CB9)
    val colorWhite =  Color(0xFFFCFDFD)

    LaunchedEffect(status) {
        if (status == Status.SUCCESS) {

              viewModel.setStatus()
              onSuccessLogin()

        }
    }
    val message by viewModel.message.observeAsState("")
    if (status == Status.LOADING) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       val (focusRequest) = FocusRequester.createRefs()
        val keyboardController = LocalSoftwareKeyboardController.current
        var showPassword by remember {
            mutableStateOf(false)
        }

        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color),
            modifier = Modifier.padding(MEDIUM_PADDING),
            value = username,
            onValueChange = { username = it },
            label = {
                Text(text = "Email")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusRequest.requestFocus() }
            )
        )
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color),
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .focusRequester(focusRequest),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = "Password")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    if (showPassword) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
                            contentDescription = "passwordoff"
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_visibility_24),
                            contentDescription = "passwordon"
                        )
                    }
                }
            }
        )
        if (status == Status.ERROR) {
            Text(text = message)
            if (isActive.equals("0")) {
                val intent = Intent(context, ResetPasswordActivity::class.java)
                    intent.putExtra("email",email )
                context.startActivity(intent)
            }
        }

        Button( colors = ButtonDefaults.buttonColors(backgroundColor = color, contentColor = colorWhite),
            onClick = { viewModel.login(username, password) }) {
            Text(text = "LOGIN",

            )
        }
    }
}