package com.accenture.roby.base

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import java.lang.Exception


open class BaseActivity : AppCompatActivity() {
    val SUCCESS = "sukses"
    val ERROR = "error"

    fun toast(message: String) = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    fun toastLong(message: String) = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    fun snack(view: View, message: String) = Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

    fun goto(classes: Any) = startActivity(Intent(this, classes as Class<*>))

    fun gotoWithClose(classes: Any) {
        startActivity(Intent(this, classes as Class<*>))
        this.finish()
    }


    fun gotoWithParam(classes: Any, name: List<String>, value: List<String>) {
        val intent= Intent(this, classes as Class<*>)
        for (i in name.withIndex()){
            intent.putExtra(i.value, value[i.index])
        }
        startActivity(intent)
    }

    fun gotoWithClearClose(classes: Any) {
        finish()
        Intent(this, classes as Class<*>).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

     fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun initRecyclerHorizontal(recycler: RecyclerView?) {
        val mManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler!!.apply {
            layoutManager = mManager
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }



    protected fun initRecycler(recycler: RecyclerView?) {
        val mManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler!!.apply {
            layoutManager = mManager
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    protected fun initRecyclerLinear(recycler: RecyclerView?, mLayout: LinearLayoutManager?) {
//        val mManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler!!.apply {
            layoutManager = mLayout
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    protected fun initRecyclerGrid(recycler: RecyclerView?) {
        val mManager = GridLayoutManager(this, 2)
        recycler!!.apply {
            layoutManager = mManager
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }



    fun topBar(){
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    }

    fun myToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 apidev.lifeinspectrum.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }



}
