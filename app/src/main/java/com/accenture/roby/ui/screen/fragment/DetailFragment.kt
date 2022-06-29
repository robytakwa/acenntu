package com.accenture.roby.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.accenture.roby.data.model.response.UserResponse
import com.mitralaundry.xpro.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    companion object {
        const val EXTRA_USER = "user"

    }

    private lateinit var binding: FragmentDetailBinding
    private var getIntentData: UserResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        getIntentData = arguments?.getParcelable(EXTRA_USER)

        Glide.with(this)
            .load(getIntentData?.avatarUrl)
            .into(binding.ivAvatar)
        return binding.root
    }

}
