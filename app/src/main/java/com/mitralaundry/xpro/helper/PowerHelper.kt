@file:JvmName("UtilsKt")

package com.spe.spectrum.helper

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Roby
 * 07/02/2022
 */



inline fun <T: Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply { arguments = Bundle().apply(argsBuilder) }
fun <T: Activity> T.getExtraString(key: String?): String = intent.getStringExtra(key)!!
fun <T: Activity> T.getExtraInt(key: String?): Int = intent.getIntExtra(key, 0)


fun <T: Fragment> T.getExtraString(key: String?): String = requireArguments().getString(key, "")!!
fun <T: Fragment> T.getExtraInt(key: String?): Int = requireArguments().getInt(key, 0)

