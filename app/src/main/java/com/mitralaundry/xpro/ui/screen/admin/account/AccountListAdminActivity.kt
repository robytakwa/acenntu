package com.mitralaundry.xpro.ui.screen.admin.account

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.DataUser
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.AccountListActivityBinding
import com.mitralaundry.xpro.databinding.ReportMachineActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.helper.EndlessRecyclerViewScrollListener
import com.mitralaundry.xpro.ui.adapter.AccountListAdminAdapter
import com.mitralaundry.xpro.ui.screen.fragment.SuccesDialogFragment
import com.mitralaundry.xpro.ui.screen.fragment.UserResetFragment
import com.spe.spectrum.helper.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountListAdminActivity : BaseActivity() {
    private lateinit var mAdapter : AccountListAdminAdapter
    private val viewModel : AccountAdminViewModel by viewModels()
    var userId=""
    private lateinit var binding : AccountListActivityBinding
    private var succesDialogFragment = SuccesDialogFragment()
    private var userResetFragment = UserResetFragment({
        viewModel.resetUserAdmin(userId)
    })
    private var dataUser: List<DataUser> = listOf()
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    var mLayoutManager: LinearLayoutManager? = null
    private var lastpage = 0
    var page = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AccountListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAdapter = AccountListAdminAdapter()
        initRecycler(binding.rvListAccount)
        binding.rvListAccount.adapter = mAdapter
        binding.toolbar.tvToolbar.text = "Account List"
        viewModel.getListAccountAdmin()
        initDataObserve()
        initClickListener()
    }

    private fun initDataObserve() {
       viewModel.lastPackage.observe(this) {
         lastpage = it
           }

        viewModel.dataList.observe(this) {
            dataUser = it

                mAdapter.setData(it)

            mAdapter.SetOnItemClickListener(object : AccountListAdminAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    userId = it[position].userId
                    userResetFragment.withArgs {
                        putString("user_id",it[position].userId )
                    }.show(supportFragmentManager, "")
                }

            })
        }

        viewModel.dataListSearch.observe(this) {

            if(it.isEmpty()){
                myToast("Data Tidak Ditemukan")
            }else {
                mAdapter.setData(it)

            }

            mAdapter.SetOnItemClickListener(object : AccountListAdminAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    userId = it[position].userId


                    userResetFragment.withArgs {
                        putString("user_id",it[position].userId )
                    }.show(supportFragmentManager, "")
                }

            })



        }


        viewModel.statusList.observe(this) {
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

        viewModel.statusReset.observe(this) {
            if (it == Status.LOADING) {
                CustomLoading.showLoading(this)


            } else {
                CustomLoading.hideLoading()
                succesDialogFragment.show(supportFragmentManager, "")
            }
        }

    }

    private fun initClickListener() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mLayoutManager) {
            override fun onLoadMore(p: Int, totalItemsCount: Int, view: RecyclerView?) {
                page++
                if (page <= lastpage) {
                    viewModel.getListAccountAdmin()
                }
            }
        }
        binding.rvListAccount.addOnScrollListener(scrollListener!!)
        binding.toolbar.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.toolbar.ivAdd.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)

        }

        binding.searchAccountList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    viewModel.getListAccountAdmin()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getListAccountAdminSearch(query)
                return false
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    }
