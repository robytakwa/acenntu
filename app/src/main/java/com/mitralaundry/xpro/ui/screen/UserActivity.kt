package com.mitralaundry.xpro.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.UserResponse
import com.mitralaundry.xpro.databinding.UserListActivityBinding
import com.mitralaundry.xpro.ui.adapter.UserNewAdapter
import com.mitralaundry.xpro.ui.screen.merchant.kelolakasir.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : BaseActivity() {

    private lateinit var mAdapter: UserNewAdapter
    private val viewModel: ViewModelUser by viewModels()
    private lateinit var binding: UserListActivityBinding

    companion object {
        const val EXTRA_USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAdapter = UserNewAdapter()
        initRecycler(binding.rvListAccount)
        binding.rvListAccount.adapter = mAdapter
        viewModel.getListUser()
        initDataObserve()
    }

    private fun initDataObserve() {
        viewModel.dataUser.observe(this) {
            mAdapter.setData(it, this)
            actionToDetail(it)
        }
    }

    private fun actionToDetail(user: List<UserResponse>) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(EXTRA_USER, user.toString())
            startActivity(intent)

    }


}
