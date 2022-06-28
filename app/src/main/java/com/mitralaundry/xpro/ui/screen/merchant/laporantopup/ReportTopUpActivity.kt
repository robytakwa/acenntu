package com.mitralaundry.xpro.ui.screen.merchant.laporantopup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.ReportMachineActivityBinding
import com.mitralaundry.xpro.databinding.UploadLaporanActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.adapter.ReportTopUpAdapter
import com.mitralaundry.xpro.ui.screen.fragment.TglTopUpDialogFragment
import com.spe.spectrum.helper.withArgs
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

@AndroidEntryPoint
class ReportTopUpActivity : BaseActivity(), TglTopUpDialogFragment.DialogListener {
    private var outleId = ""
    private var startDate = ""
    private var endDate = ""

    private lateinit var mAdapter: ReportTopUpAdapter
    private val viewModel by viewModels<ViewModelReportTopup>()
    private lateinit var binding: ReportMachineActivityBinding
    private var totalPendapatan = ""

    private var tglTopUpDialogFragment = TglTopUpDialogFragment {
        viewModel.getReport(startDate, endDate, outleId)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReportMachineActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAdapter = ReportTopUpAdapter()
        initRecycler(binding.rvListReportMachine)
        binding.rvListReportMachine.adapter = mAdapter
        binding.toolbar.tvToolbar.text = "Laporan TopUp"
        binding.toolbar.ivAdd.visibility = android.view.View.GONE
        outleId = intent?.extras?.get("outletId") as String
        val outletName = intent?.extras?.get("outletName")
        binding.tvOutletId.text = outleId
        binding.tvName.text = outletName.toString()

        viewModel.getReport("", "", outleId)
        initListener()
        initDataObserve()

    }


    private fun formatDate(inputPattern: String?, outputPattern: String?, date: String?): String? =
        SimpleDateFormat(outputPattern!!, Locale("id", "ID")).format(
            SimpleDateFormat(inputPattern!!, Locale("id", "ID")).parse(date!!)!!
        )

    private fun rupiahFormat(number: String?): String {
        val temp = when {
            number!!.contains(".") -> ceil(number.toDouble()).toInt().toString()
            number.isEmpty() -> "0"
            else -> number
        }
        return "Rp ${NumberFormat.getIntegerInstance(Locale.GERMANY).format(temp.toLong())}"
    }


    private fun initListener() {
        binding.toolbar.btnBack.setOnClickListener {
            finish()
        }

        binding.llPilihTgl.setOnClickListener {
            tglTopUpDialogFragment.withArgs {
            }.show(supportFragmentManager, "")
        }

    }

    private fun initDataObserve() {
        viewModel.status.observe(this) {
            if (it == Status.LOADING) {
                mAdapter.clearData()
                CustomLoading.showLoading(this)

            } else {
                CustomLoading.hideLoading()
                if (startDate.isNotEmpty() && endDate.isNotEmpty()) {

                    binding.tvPilihTgl.text = formatDate(
                        "yyyy-MM-dd",
                        "dd MMMM yyyy",
                        startDate
                    ) + " - " + formatDate("yyyy-MM-dd", "dd MMMM yyyy", endDate)
                } else {
                    binding.tvPilihTgl.text = "Pilih Tanggal"
                }
            }
        }


        viewModel.data.observe(this) {
            mAdapter.setData(it)
            totalPendapatan = it.sumBy { it.topup }.toString()
            binding.tvTotalTransaction.text = rupiahFormat(totalPendapatan)


        }

    }

    override fun listLoadByDate(dateStart: String, dateEnd: String) {
        startDate = dateStart
        endDate = dateEnd

        println("start date saya : $startDate")
        println("end date saya : $endDate")
    }

}