package com.mitralaundry.xpro.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mitralaundry.xpro.databinding.DialogSuccesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccesDialogFragment : DialogFragment() {
    private lateinit var binding: DialogSuccesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_succes, container, false)
        binding = DialogSuccesBinding.inflate(inflater)
        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.btnOk.setOnClickListener {
            this.dismiss()
        }
    }
}