package com.mitralaundry.xpro.ui.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mitralaundry.xpro.databinding.DialogTabCardBinding

class TabCardDialogFragment(private var actionYes: () -> Unit) : DialogFragment() {

    private lateinit var binding: DialogTabCardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogTabCardBinding.inflate(inflater, container, false)
        binding.btnOk.setOnClickListener {
            actionYes().also {
                this.dismiss()
            }
        }
        return binding.root
    }
}