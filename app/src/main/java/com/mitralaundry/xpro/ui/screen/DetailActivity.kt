package com.mitralaundry.xpro.ui.screen

import android.os.Bundle
import android.os.PersistableBundle
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.databinding.ActivityDetailBinding

class DetailActivity  : BaseActivity() {

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
