package com.mitralaundry.xpro.ui.screen.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.util.Constant
import kotlinx.coroutines.delay

@Composable
fun SplashScreenCompose(
    viewModel: ViewModelSplash,
    onLogin: () -> Unit,
    needLogin: () -> Unit
) {

    viewModel.check()
    val isLogin by viewModel.isLogin.observeAsState()
    isLogin?.let {
        LaunchedEffect(isLogin) {
            if (isLogin == true) {
                println("is login")
                onLogin()
                delay(2000)
                viewModel.finishNavigateToHome()
            }

            if (isLogin == false) {
                needLogin()
                println("need login")
                delay(2000)
                viewModel.finishNavigateToHome()
            }
        }
    }



    Box(
        modifier = Modifier.fillMaxSize().background(color = Constant.HexToJetpackColor.getColor("FFFFFF")),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash), contentDescription = "logo",
        )
    }
}


