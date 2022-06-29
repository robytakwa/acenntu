package com.accenture.roby.util

import androidx.compose.ui.graphics.Color


object Constant {

    const val BASE_URL = "https://api.github.com/"

    const val BLOCK_CODE = 1
    const val BLOCK_SALDO = 2
    const val BLOCK_USER = 4
    const val BLOCK_UPDATE_PRICE = 8
    const val BLOCK_BRAND = 9
    const val BLOCK_DURATION = 10
    const val BLOCK_PULSE = 12
    const val BLOCK_TITLE = 13
    const val BLOCK_COUNTER = 14
    const val BLOCK_TYPE_MACHINE = 16
    const val BLOCK_COUNTER_PRICE = 17
    const val BLOCK_MAC_ADDRESS = 18
    const val BLOCK_OUTLET_ID = 22
    const val BLOCK_MEMBER_ID = 24
    const val BLOCK_DURATION_COUNTER = 25
    const val BLOCK_MERCHANT_ID = 26
    const val BLOCK_ONLINE = 28

    const val CODE_USER = "USR2021"
    const val CODE_MASTER = "MSTRKEY"
    const val CODE_PRICE = "MSTRPRICE"
    const val CODE_RESET = "MSTRREST"
    const val CODE_COUNTER = "MSTRCOUNTER"
    const val CODE_MAC = "MSTRMACKEY"

    const val QUERY_PER_PAGE = 10
    const val DEFAULT_SALDO = 10000

    val KEY_DEFAULT = byteArrayOf(
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte(),
        0xFF.toByte()
    )

    object HexToJetpackColor {
        fun getColor(colorString: String): Color {
            return Color(android.graphics.Color.parseColor("#" + colorString))
        }
    }
}