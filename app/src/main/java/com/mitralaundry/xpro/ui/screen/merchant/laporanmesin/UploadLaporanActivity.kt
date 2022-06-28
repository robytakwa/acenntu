package com.mitralaundry.xpro.ui.screen.merchant.laporanmesin

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
import com.mitralaundry.xpro.databinding.UploadLaporanActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.screen.fragment.SuccesSaveDialogFragment
import com.mitralaundry.xpro.util.MifareUtils
import com.spe.spectrum.helper.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadLaporanActivity : BaseActivity() {

    private lateinit var binding: UploadLaporanActivityBinding
     private val viewModel: ViewModelReportMesin by viewModels()

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
        var outletId = intent.getStringExtra("outletId")

        binding.toolbar.tvToolbar.text = "Upload Laporan"
        binding.toolbar.ivAdd.visibility = android.view.View.GONE

        binding.btnOk.setOnClickListener {
            viewModel.uploadLaporan( outletId.toString())
        }

        initNFC()
        initListener()
        initDataObserve()
    }

    private fun initDataObserve() {
        viewModel.statusUpload.observe(this) {

            if (it == Status.LOADING) {
                CustomLoading.showLoading(this)

            } else {
                CustomLoading.hideLoading()
                succesDialogFragment.withArgs {
                }.show(supportFragmentManager, "")

            }

        }

        viewModel.textInfo.observe(this) {
            binding.tvDialog.text = it
        }

        viewModel.disableButton.observe(this) {
            if (it) {
                binding.btnOk.isEnabled = true
            }

        }

    }

    private fun initListener() {
        binding.toolbar.btnBack.setOnClickListener {
           onBackPressed()
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