package com.mitralaundry.xpro.ui.screen.admin.prices

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.PriceAdminActivityBinding
import com.mitralaundry.xpro.databinding.PriceAdminDetailActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.adapter.PriceAdminAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PriceAdminActiviy : BaseActivity() {
    private lateinit var mAdapter: PriceAdminAdapter
    private val viewModel: PriceAdminViewModel by viewModels()
    private lateinit var binding: PriceAdminActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PriceAdminActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = PriceAdminAdapter()
        initRecycler(binding.rvListPriceAdmin)
        binding.rvListPriceAdmin.adapter = mAdapter
        binding.toolbar.ivAdd.visibility = View.INVISIBLE
        binding.toolbar.tvToolbar.text = "Pricing"
        viewModel.getPriceAdmin()
        initLoadData()
        initClickLiester()
        initStatus()


    }

    private fun initLoadData() {
        viewModel.dataSearch.observe(this) {
            if(it.isEmpty()){
                myToast("Data Tidak Ditemukan")
            }else {
                mAdapter.setData(it)

            }


        }
    }



        private fun initStatus() {

            viewModel.status.observe(this) {
                if (it == Status.LOADING) {
                    mAdapter.clearData()
                    CustomLoading.showLoading(this)

                } else {
                    CustomLoading.hideLoading()

                }

            }

            viewModel.statusSearch.observe(this) {
                if (it == Status.LOADING) {
                    mAdapter.clearData()
                    CustomLoading.showLoading(this)

                } else {
                    CustomLoading.hideLoading()


                }

            }
        }



    private fun initClickLiester() {


        binding.toolbar.btnBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.data.observe(this) {
            mAdapter.setData(it)


            binding.searchPriceList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty()) {
                        viewModel.getPriceAdmin()
                    }
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
//                    val filterdNames: ArrayList<ResponsePriceAdmin> = ArrayList()
//                    for (i in 0 until it.size) {
//                        if (it[i].name!!.toLowerCase().contains(query.toLowerCase())) {
//                            filterdNames.add(it[i])
//                        }
//                    }

                    viewModel.getPriceAdminSearch(query)
                    return false
                }
            })


            mAdapter.SetOnItemClickListener(object : PriceAdminAdapter.OnItemClickListener {


                override fun onItemClick(position: Int) {
                    val intent =
                        Intent(this@PriceAdminActiviy, DetailPriceAdminActivity::class.java)
                    intent.putExtra("outlet_id", it[position].outletId)
                    intent.putExtra("merchant_id", it[position].merchantId)
                    intent.putExtra("name", it[position].name)
                    startActivity(intent)

                }
            })


        }

    }
}
