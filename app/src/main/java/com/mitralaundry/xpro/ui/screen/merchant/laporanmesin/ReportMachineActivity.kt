package com.mitralaundry.xpro.ui.screen.merchant.laporanmesin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.ReportMesin
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.AccountListActivityBinding
import com.mitralaundry.xpro.databinding.ReportMachineActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.adapter.ReportMachineAdapter
import com.mitralaundry.xpro.ui.screen.fragment.TanggalDialogFragment
import com.spe.spectrum.helper.withArgs
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

@AndroidEntryPoint
class ReportMachineActivity : BaseActivity(), TanggalDialogFragment.DialogListener {
    private var outletId = ""
    private var startDate = ""
    private var endDate = ""
    private lateinit var mAdapter: ReportMachineAdapter
    private val viewModel: ViewModelReportMesin by viewModels()
    private lateinit var binding: ReportMachineActivityBinding

    private var totalPendapatan = ""
    private var reportMesin: List<ReportMesin> = listOf()


    private var tanggalDialogFragment = TanggalDialogFragment {
        viewModel.getReportFilter(startDate, endDate, outletId)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReportMachineActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAdapter = ReportMachineAdapter()
        initRecycler(binding.rvListReportMachine)
        binding.rvListReportMachine.adapter = mAdapter
        binding.toolbar.tvToolbar.text = "Laporan Mesin"
        outletId = intent?.extras?.get("outletId") as String
        val outletName = intent?.extras?.get("outletName")
        binding.tvOutletId.text = outletId
        binding.tvName.text = outletName.toString()
        binding.tvPilihTgl.text = "Pilih Tanggal"
        viewModel.getReportFilter("", "", outletId)
        initListener()
        initDataObserve()

    }

    private fun rupiahFormat(number: String?): String {
        val temp = when {
            number!!.contains(".") -> ceil(number.toDouble()).toInt().toString()
            number.isEmpty() -> "0"
            else -> number
        }
        return "Rp ${NumberFormat.getIntegerInstance(Locale.GERMANY).format(temp.toLong())}"
    }


    private fun initListener() {

        binding.toolbar.ivAdd.setOnClickListener {
            val intent = Intent(this@ReportMachineActivity, UploadLaporanActivity::class.java)
            intent.putExtra("outletId", outletId)
            this@ReportMachineActivity.startActivity(intent)
        }
        binding.llPilihTgl.setOnClickListener {
            tanggalDialogFragment.withArgs {
            }.show(supportFragmentManager, "")
        }

        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }

    }


    private fun initDataObserve() {

        viewModel.statusFilter.observe(this) {
            if (it == Status.LOADING) {
                mAdapter.clearData()
                CustomLoading.showLoading(this)

            } else {
                CustomLoading.hideLoading()
                binding.tvPilihTgl.text = formatDate(
                    "yyyy-MM-dd",
                    "dd MMMM yyyy",
                    startDate
                ) + " - " + formatDate("yyyy-MM-dd", "dd MMMM yyyy", endDate)

            }
        }

        viewModel.data.observe(this) {
            reportMesin = it
            mAdapter.setData(it)
            totalPendapatan = it.sumBy { it.pendapatan }.toString()
            binding.tvTotalTransaction.text = rupiahFormat(totalPendapatan.toString())


        }

        viewModel.dataFilter.observe(this) {
            reportMesin = it
            mAdapter.setData(it)
            totalPendapatan = it.sumBy { it.pendapatan }.toString()
            binding.tvTotalTransaction.text = rupiahFormat(totalPendapatan.toString())


        }

    }

    private fun formatDate(inputPattern: String?, outputPattern: String?, date: String?): String? =
        SimpleDateFormat(outputPattern!!, Locale("id", "ID")).format(
            SimpleDateFormat(inputPattern!!, Locale("id", "ID")).parse(date!!)!!
        )


    override fun listLoadByDate(dateStart: String, dateEnd: String) {
        startDate = dateStart
        endDate = dateEnd

        println("start date saya : $startDate")
        println("end date saya : $endDate")
    }


}

