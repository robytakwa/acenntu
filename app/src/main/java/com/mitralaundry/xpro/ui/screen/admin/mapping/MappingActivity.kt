package com.mitralaundry.xpro.ui.screen.admin.mapping

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.MifareClassic
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.mitralaundry.xpro.MainActivity
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.MappingActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.adapter.MappingListAdapter
import com.mitralaundry.xpro.ui.screen.fragment.TabCardDialogFragment
import com.mitralaundry.xpro.util.MifareUtils
import com.spe.spectrum.helper.getExtraString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MappingActivity : BaseActivity() {
    private var outletId = ""
    private var outletName = ""
    private lateinit var mAdapter: MappingListAdapter
    private val viewModel: ViewModelMapping by viewModels()
    private lateinit var binding: MappingActivityBinding

    private var availableNfc = false
    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var mPendingIntent: PendingIntent
    private var mIntentFilters: Array<IntentFilter> = arrayOf()
    private var mTechList: Array<Array<String>> = arrayOf(arrayOf())
    private var positionCard = 0

    private var tabCardDialogFragment = TabCardDialogFragment {
        viewModel.writeDataMappingtoCard(this, positionCard)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.mapping_activity)
        binding = MappingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        outletId = getExtraString("outletId")
        outletName = getExtraString("outletName")
        mAdapter = MappingListAdapter()
        initRecycler(binding.rvListMapping)
        binding.rvListMapping.adapter = mAdapter
        binding.toolbar.ivAdd.visibility = View.GONE
        binding.toolbar.tvToolbar.text = "Mapping Device"
        binding.tvOutletId.text = "Outlet ID : $outletId"
        binding.tvOutletName.text = outletName
        initClick()
        initNFC()
        loadData()
    }

    private fun loadData(){
        viewModel.getListMapping(outletId)
        viewModel.statusList.observe(this) {
            if (it == Status.LOADING) {
                CustomLoading.showLoading(this)
            } else {
                CustomLoading.hideLoading()
            }
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
    private fun initClick() {
        binding.toolbar.btnBack.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        viewModel.dataList.observe(this) {

            mAdapter.setData(it)

            mAdapter.setOnItemClickListener(object : MappingListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    println("click card")
                    val intent = Intent(this@MappingActivity, UpdateMappingActivity::class.java)
                    intent.putExtra("mapping_id", it[position].mappingId)
                    intent.putExtra("outlet_id", it[position].outletId)
                    intent.putExtra("status", it[position].status!!)
                    intent.putExtra("is_active", it[position].isActive!!)
                    intent.putExtra("outlet_name", outletName)
                    startActivity(intent)
                }
            })

            mAdapter.setOnItemCardClickListener(object : MappingListAdapter.OnItemCardClickListener {
                override fun onItemCardClick(position: Int) {
                    positionCard = position
                    tabCardDialogFragment.show(supportFragmentManager, "")
                }
            })
        }

        binding.ivAddMapping.setOnClickListener {
            val intent = Intent(this@MappingActivity, CreateMappingActivity::class.java)
            intent.putExtra("outlet_id_create", outletId)
            intent.putExtra("outlet_name_create", outletName)
            startActivity(intent)
        }

        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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
        viewModel.addMifare(mifare, this)
    }
}