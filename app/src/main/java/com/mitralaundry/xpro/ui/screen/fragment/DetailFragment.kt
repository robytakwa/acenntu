package com.mitralaundry.xpro.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mitralaundry.xpro.data.model.response.UserResponse
import com.mitralaundry.xpro.databinding.FragmentDetailBinding
import com.mitralaundry.xpro.ui.screen.activity.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    companion object {
        const val EXTRA_USER = "user"

    }

    private lateinit var binding: FragmentDetailBinding
    private var getIntentData: UserResponse? = null
    private val viewModel: ViewModelMain by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        getIntentData = arguments?.getParcelable(EXTRA_USER)

        if (getIntentData != null) {
            viewModel.getDetailUser(getIntentData?.login ?: "")

        }
        initDataObserve()

        return binding.root
    }

    private fun initDataObserve() {
        viewModel.dataDetail.observe(viewLifecycleOwner) {
            binding.llDetail.tvName.text = it.name
            binding.llDetail.tvUsername.text = it.login
            binding.llDetail.tvAddress.text = it.location ?: "-"
            binding.llDetail.tvWorkplace.text = it.company ?: "-"
            binding.llDetail.tvFollower.text = it.followers.toString()
            binding.llDetail.tvFollowing.text = it.following.toString()
            binding.llDetail.tvRepository.text = it.publicRepos.toString()

            Glide.with(this)
                .load(it.avatarUrl)
                .into(binding.ivAvatar)

        }
    }


}
