package com.accenture.roby.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.accenture.roby.R

object CustomLoading {
    var dialog: Dialog? = null
    fun showLoading(context: Context?) {
        dialog = Dialog(context!!)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = LayoutInflater.from(context).inflate(R.layout.custom_loading_dialog, null)
        dialog?.setContentView(view)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window!!.attributes = WindowManager.LayoutParams().apply {
            copyFrom(dialog?.window!!.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER
        }
        dialog?.setCancelable(false)
        dialog?.show()
    }

    fun hideLoading() {
        dialog?.dismiss()
    }
}