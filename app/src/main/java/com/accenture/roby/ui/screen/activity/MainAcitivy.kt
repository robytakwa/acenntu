package com.accenture.roby.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mitralaundry.xpro.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }
}