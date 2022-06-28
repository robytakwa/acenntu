package com.mitralaundry.xpro.ui.screen.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.mitralaundry.xpro.MainActivity
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.databinding.DialogSuccesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccesSaveDialogFragment : DialogFragment() {
    private lateinit var binding: DialogSuccesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DialogSuccesBinding.inflate(inflater) // DataBindingUtil.inflate(inflater, R.layout.dialog_succes, container, false)

        binding.tvDialogSucces.text = getString(R.string.save_success)
        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.btnOk.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            dismiss()

        }

    }


}