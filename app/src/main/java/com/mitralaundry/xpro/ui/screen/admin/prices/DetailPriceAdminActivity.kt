package com.mitralaundry.xpro.ui.screen.admin.prices

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.PriceAdminDetailActivityBinding
import com.mitralaundry.xpro.databinding.UpdateMappingActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.adapter.DetailPriceAdminAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPriceAdminActivity : BaseActivity() {
    private lateinit var mAdapter: DetailPriceAdminAdapter
    private val viewModel: PriceAdminDetailViewModel by viewModels()
    private lateinit var binding: PriceAdminDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PriceAdminDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = DetailPriceAdminAdapter()
        initRecycler(binding.rvListPriceAdminDetail)
        binding.rvListPriceAdminDetail.adapter = mAdapter
        binding.toolbar.ivAdd.visibility = View.INVISIBLE
        binding.toolbar.tvToolbar.text = "Price Detail"
        val merchantId = intent.getStringExtra("merchant_id")
        val name = intent.getStringExtra("name")
        val outletId = intent?.extras?.get("outlet_id")
        binding.tvMerchantId.text = merchantId
        binding.tvOutletId.text = outletId.toString()
        binding.tvName.text = name

        viewModel.getPriceAdminDetail(outletId.toString())
        initLoadData()
        initClick()


    }

    private fun initLoadData() {
        viewModel.statusPriceDetail.observe(this) {
            if (it == Status.LOADING) {
                CustomLoading.showLoading(this)

            } else {
                CustomLoading.hideLoading()

            }

        }

    }


    private fun initClick() {
        binding.toolbar.btnBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.dataPriceDetail.observe(this) {
            mAdapter.setData(it)

            mAdapter.SetOnItemClickListener(object : DetailPriceAdminAdapter.OnItemClickListener {

                override fun onItemClick(position: Int) {
                    val intent =
                        Intent(this@DetailPriceAdminActivity, PriceAdminUpdateActivity::class.java)
                    intent.putExtra("mapping_id", it[position].mappingId)
                    intent.putExtra("machine", it[position].name)

                    startActivity(intent)

                }
            })


        }
    }
}