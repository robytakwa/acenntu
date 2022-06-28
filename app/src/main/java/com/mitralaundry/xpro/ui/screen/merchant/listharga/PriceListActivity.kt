package com.mitralaundry.xpro.ui.screen.merchant.listharga

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.PriceListActiviyBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.adapter.PriceListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceListActivity : BaseActivity() {
    private lateinit var mAdapter: PriceListAdapter
    private val viewModel: PriceListViewModel by viewModels()
    private lateinit var binding: PriceListActiviyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = PriceListActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAdapter = PriceListAdapter()
        initRecycler(binding.rvListPrice)
        binding.rvListPrice.adapter = mAdapter
        binding.toolbar.ivAdd.visibility = View.GONE
        binding.toolbar.tvToolbar.text = "Price Detail"

        val outletId = intent?.extras?.get("outletId")
        val outletName = intent?.extras?.get("outletName")
        binding.tvOutletId.text = outletId.toString()
        binding.tvName.text = outletName.toString()
        viewModel.getPriceList()
        viewModel.setOutletId(outletId.toString())
        initClickListener()
        initLoadData()
    }

    private fun initClickListener() {
        binding.toolbar.btnBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.data.observe(this) {
            mAdapter.setData(it)
            mAdapter.SetOnItemClickListener(object : PriceListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@PriceListActivity, UpdatePriceActivity::class.java)
                    intent.putExtra("mappingId", it[position].mappingId)
                    intent.putExtra("machine", it[position].name)
                    intent.putExtra("date", it[position].createdAt)
                    startActivity(intent)
                }
            })
        }
    }

    private fun initLoadData() {
        viewModel.statusList.observe(this) {
            if (it == Status.LOADING) {
                CustomLoading.showLoading(this)
            } else {
                CustomLoading.hideLoading()
            }
        }
    }
}