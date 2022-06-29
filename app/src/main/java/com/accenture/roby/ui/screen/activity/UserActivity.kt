package com.accenture.roby.ui.screen.activity

import android.os.Bundle
import android.os.PersistableBundle
import com.accenture.roby.R
import com.accenture.roby.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : BaseActivity()    {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_us)

    }
}