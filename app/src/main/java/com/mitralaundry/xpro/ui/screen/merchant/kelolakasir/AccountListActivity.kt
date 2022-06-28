package com.mitralaundry.xpro.ui.screen.merchant.kelolakasir

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.DataUser
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.AccountListActivityBinding
import com.mitralaundry.xpro.databinding.PriceListActiviyBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.adapter.UserAdapter
import com.mitralaundry.xpro.ui.screen.fragment.SuccesDialogFragment
import com.mitralaundry.xpro.ui.screen.fragment.UserResetFragment
import com.spe.spectrum.helper.withArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccountListActivity : BaseActivity() {
    private lateinit var mAdapter: UserAdapter
    private val viewModelUser: ViewModelUser by viewModels()
    private lateinit var binding: AccountListActivityBinding
    var outletReset = ""
    var userId = ""
    var outletCreateAccount = ""
    private var outletId = ""
    private var succesDialogFragment = SuccesDialogFragment()
    private var userResetFragment = UserResetFragment({
        viewModelUser.resetUser(outletReset, userId)
    })

    private var dataUser: List<DataUser> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AccountListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        outletId = intent.getStringExtra("outlet_id")!!
        mAdapter = UserAdapter()
        initRecycler(binding.rvListAccount)
        binding.rvListAccount.adapter = mAdapter
        binding.toolbar.tvToolbar.text = "Account List"
        viewModelUser.listUser()
        initClickListener()
        initObserve()

    }

    private fun initObserve() {
        viewModelUser.data.observe(this) {
            dataUser = it
            mAdapter.setData(it)

            outletCreateAccount = it[0].outletId

            mAdapter.SetOnItemClickListener(object : UserAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    outletReset = it[position].outletId
                    userId = it[position].userId

                    userResetFragment.withArgs {
                        putString("outlet_id", it[position].outletId)
                        putString("user_id", it[position].userId)
                    }.show(supportFragmentManager, "")
                }

            })
        }


        viewModelUser.status.observe(this) {
            if (it == Status.LOADING) {
                mAdapter.clearData()
                CustomLoading.showLoading(this)

            } else {
                CustomLoading.hideLoading()

            }
        }

        viewModelUser.statusSearch.observe(this) {
            if (it == Status.LOADING) {
                mAdapter.clearData()
                CustomLoading.showLoading(this)

            } else {
                CustomLoading.hideLoading()

            }

        }

        viewModelUser.statusReset.observe(this) {
            if (it == Status.LOADING) {
                CustomLoading.showLoading(this)

            } else {
                CustomLoading.hideLoading()
                succesDialogFragment.show(supportFragmentManager, "")

            }
        }

    }

    private fun initClickListener() {

        binding.toolbar.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.toolbar.ivAdd.setOnClickListener {
            val intent = Intent(this, AddAccountActivity::class.java)
            intent.putExtra("outlet_id", outletId)
            startActivity(intent)

        }

        binding.searchAccountList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    viewModelUser.listUser()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {


                viewModelUser.listUserSearch(query)
                return false
            }
        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}