
package com.mitralaundry.xpro.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.data.model.response.UserResponse
import com.mitralaundry.xpro.databinding.FragmentUserBinding
import com.mitralaundry.xpro.ui.adapter.UserNewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    private lateinit var mAdapter: UserNewAdapter
    private val viewModel: ViewModelMain by viewModels()
    private lateinit var binding: FragmentUserBinding
    private lateinit var userList: List<UserResponse>

    companion object {
        const val EXTRA_USER = "user"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        mAdapter = UserNewAdapter()
        initRecycler(binding.rvListAccount)
        binding.rvListAccount.adapter = mAdapter
        viewModel.getListUser()
        initDataObserve()
        initSearch()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            mAdapter.SetOnItemClickListener(object : UserNewAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    actionToDetail(view,userList,position)
                }

            })

    }

    private fun initSearch() {
        binding.toolbar.searchUser.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                   mAdapter.clearData()
                    viewModel.getListUser()
                } else {
                    mAdapter.filter.filter(newText)
                }
                return false
            }
        })


    }

    private fun initRecycler(recycler: RecyclerView?) {
        val mManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler!!.apply {
            layoutManager = mManager
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }


    private fun initDataObserve() {
        viewModel.dataUser.observe(viewLifecycleOwner) {
            userList = it
            mAdapter.setData(it, context!!)

        }
    }

    private fun actionToDetail(view: View, user: List<UserResponse>, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_USER, user[position])
        findNavController(view).navigate(R.id.action_userFragment_to_detailFragment, bundle)
    }

}