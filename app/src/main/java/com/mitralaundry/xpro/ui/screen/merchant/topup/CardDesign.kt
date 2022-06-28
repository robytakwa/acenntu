package com.mitralaundry.xpro.ui.screen.merchant.topup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mitralaundry.xpro.R
import java.text.NumberFormat

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CardDesign(
    username: String,
    saldo: Int,
    isCard: Boolean = true,
    visibleSaldo: Boolean = true
) {
    val heighCard = 200.dp
    val heightComponent = with(LocalDensity.current) { heighCard.toPx() }
    val colorText = Color.White
    val radius = 50f
    val saldoAnimate by animateIntAsState(
        targetValue = saldo,
        animationSpec = tween(1000)
    )
    // animated text change
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val saldoFormat = NumberFormat.getInstance().format(saldoAnimate)
    var width by remember { mutableStateOf(IntSize.Zero) }

    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(heighCard)
            .onSizeChanged {
                width = it
            }
            .drawBehind {
                val componentSize = Size(width.width.toFloat(), heightComponent)
                val colorBackground = Color(18, 99, 214, 255)
                val colorOval = Color(255, 255, 255, 50)
                drawRoundRect(
                    size = componentSize,
                    cornerRadius = CornerRadius(radius, radius),
                    color = colorBackground,
                    topLeft = Offset(
                        x = 0f,
                        y = 0f
                    )
                )
                drawOval(
                    color = colorOval,
                    size = Size(600f, 600f),
                    topLeft = Offset(
                        x = 500f,
                        y = -300f
                    )
                )
                drawOval(
                    color = colorOval,
                    size = Size(600f, 600f),
                    topLeft = Offset(
                        x = 750f,
                        y = 0f
                    )
                )
            },
    ) {
        if (isCard) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedVisibility(visibleState = state) {
                    username?.let {
                        Text(
                            text = it,
                            color = colorText,
                            fontSize = 30.sp,
                        )
                    }
                }
                if (visibleSaldo) {
                    Column() {
                        Text(
                            text = stringResource(R.string.balance),
                            color = colorText,
                            fontSize = 30.sp,
                        )
                        Text(
                            text = "Rp $saldoFormat",
                            color = colorText,
                            fontSize = 30.sp,
                        )
                    }
                }
            }
        } else {
            Text(text = "No Card, please tab your card")
        }
    }
}