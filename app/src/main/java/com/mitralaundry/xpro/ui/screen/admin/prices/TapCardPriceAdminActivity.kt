package com.mitralaundry.xpro.ui.screen.admin.prices

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.MifareClassic
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.PriceAdminActivityBinding
import com.mitralaundry.xpro.databinding.UploadLaporanActivityBinding
import com.mitralaundry.xpro.ui.screen.fragment.SuccesSaveDialogFragment
import com.mitralaundry.xpro.util.MifareUtils
import com.spe.spectrum.helper.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TapCardPriceAdminActivity : BaseActivity() {

    private lateinit var binding: UploadLaporanActivityBinding
    private val viewModel: PriceAdminDetailViewModel by viewModels()

    private var availableNfc = false
    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var mPendingIntent: PendingIntent
    private var mIntentFilters: Array<IntentFilter> = arrayOf()
    private var mTechList: Array<Array<String>> = arrayOf(arrayOf())
    private var succesDialogFragment = SuccesSaveDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UploadLaporanActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.tvToolbar.text = "Update Price"
        binding.toolbar.ivAdd.visibility = android.view.View.GONE
        binding.toolbar.btnBack.visibility = android.view.View.GONE
        var machine = intent.getStringExtra("machine")
        var price = intent.getStringExtra("price")
        initNFC()
        initListener(machine.toString(), price.toString())
        initDataObserve()

    }

    private fun initDataObserve() {
        viewModel.textInfo.observe(this) {
          if (it.equals("Tidak ada kartu yang terdeteksi, silakan tab kembali")) {
              binding.btnOk.isEnabled = false
          } else {
              binding.btnOk.isEnabled = true
          }
            binding.tvDialog.text = it
        }

        viewModel.disableButton.observe(this) {
            if (it) {
                binding.btnOk.isEnabled = true
            }

        }

        viewModel.statusCard.observe(this) {
            if (it == Status.SUCCESS) {
                succesDialogFragment.withArgs {
                }.show(supportFragmentManager, "")
            }
        }

    }
    override fun onBackPressed() {

    }

    private fun initListener(machine: String, price : String) {
        binding.btnOk.setOnClickListener {
            viewModel.writeDataToCard(machine, price)
        }

    }

    private fun initNFC() {
        if (MifareUtils.availableNfc(this)) {
            availableNfc = true
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
            val intent = Intent(this, javaClass).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            mPendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_MUTABLE
            )
            val filter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
            try {
                filter.addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                e.printStackTrace()
            }
            mIntentFilters = arrayOf(filter)
            mTechList = arrayOf(arrayOf(MifareClassic::class.java.name))
        }
    }


    override fun onResume() {
        super.onResume()
        if (availableNfc) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mTechList)
        }
    }

    override fun onPause() {
        super.onPause()
        if (availableNfc) {
            mNfcAdapter.disableForegroundDispatch(this)
        }
    }


    @SuppressLint("MissingSuperCall")
    override fun onNewIntent(intent: Intent?) {
        if (!NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent?.action)) {
            return
        }
        resolveIntent(intent)
    }

    private fun resolveIntent(intent: Intent?) {
        val mifare = MifareClassic.get(intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG))
        viewModel.addMifare(mifare)
    }

}