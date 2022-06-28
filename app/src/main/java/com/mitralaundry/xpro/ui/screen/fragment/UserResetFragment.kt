package com.mitralaundry.xpro.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.databinding.DialogTabCardBinding
import com.mitralaundry.xpro.databinding.LayoutBottomsheetResetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserResetFragment(private var actionYes: () -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutBottomsheetResetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LayoutBottomsheetResetBinding.inflate(inflater, container, false)
        initListener()

        return binding.root
    }

    private fun initListener() {

        binding.btnYa.setOnClickListener {
            actionYes().also {
                this.dismiss()
            }

        }

        binding.btnTidak.setOnClickListener {
            dismiss()
        }

    }


}