package com.mitralaundry.xpro.ui.screen.admin.mapping

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.ResultsItem
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.UpdateMappingActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.screen.fragment.SuccesSaveDialogFragment
import com.spe.spectrum.helper.withArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateMappingActivity : BaseActivity() {

    private var etPrice = "0"
    private var etTimeDuration = "0"
    private var etpulduration = "0"
    private var machineId = 0
    private var miconId = 0
    private var timeDuration = ""
    private var pulse = ""
    private var pulseDurationType = ""
    private var reportOnline = true

    private lateinit var binding: UpdateMappingActivityBinding
    private val viewModel: ViewModelMapping by viewModels()
    private var succesDialogFragment = SuccesSaveDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdateMappingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val outletId = intent?.extras?.get("outlet_id_create")
        val outletName = intent?.extras?.get("outlet_name_create")
        binding.tvOutletId.text = "Outlet ID : $outletId"
        binding.tvOutletName.text = outletName.toString()
        binding.toolbar.ivAdd.visibility = View.GONE
        binding.toolbar.tvToolbar.text = "Add Mapping"

        viewModel.getListDevicesMapping()
        viewModel.dataDevice.observe(this) {
            initSpinner(it, this, binding)
        }

        viewModel.statusDevice.observe(this) {
            if (it == Status.LOADING) {
                CustomLoading.showLoading(this)
            } else {
                CustomLoading.hideLoading()
            }
        }

        viewModel.statusCreate.observe(this) {
            if (it == Status.LOADING) {
                CustomLoading.showLoading(this)
            } else {
                CustomLoading.hideLoading()
                succesDialogFragment.withArgs {
                }.show(supportFragmentManager, "")
            }
        }

        initClickListener(binding)
    }

    private fun initClickListener(binding: UpdateMappingActivityBinding) {
        binding.toolbar.btnBack.setOnClickListener {
            onBackPressed()
        }

        val outletId = intent?.extras?.get("outlet_id_create")

        binding.rgOnlineReport.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_yes) {
                reportOnline = true
            } else {
                reportOnline = false
            }
        }

        binding.btnSimpan.setOnClickListener {
            etpulduration = binding.etPulseDuration.text.toString()
            etTimeDuration = binding.etTimeDuration.text.toString()
            etPrice = binding.etPrice.text.toString()
            val isOnline = binding.rbYes.isChecked

            if (etpulduration.isEmpty() ||
                etTimeDuration.isEmpty() ||
                etPrice.isEmpty() ||
                machineId == 0 ||
                miconId == 0 ||
                pulse.isEmpty() ||
                pulseDurationType.isEmpty() ||
                timeDuration.isEmpty()
            ) {
                myToast("Mohon isi semua data")
            } else {
                viewModel.createMapping(
                    outletId.toString(),
                    machineId,
                    miconId,
                    null,
                    pulse,
                    pulseDurationType,
                    etpulduration.toInt(),
                    timeDuration,
                    etTimeDuration.toInt(),
                    etPrice.toInt(),
                    "aktif",
                    isOnline,
                    reportOnline

                )
            }

        }
    }

    private fun initSpinner(
        listResult: List<ResultsItem>,
        context: CreateMappingActivity,
        binding: UpdateMappingActivityBinding,
    ) {
        val listMachine: MutableList<String> = ArrayList()
        listMachine.add("-Pilih-")
        val listMachineResult: MutableList<ResultsItem> = ArrayList()

        for (element in listResult) {
            if (element.type == "Dry Machine" || element.type == "Wash Machine") {
                listMachine.add(element.name!!)
                listMachineResult.add(element)
            }
        }

        val adapterMachine: ArrayAdapter<String> =
            ArrayAdapter(context, android.R.layout.simple_spinner_item, listMachine)
        adapterMachine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMachine.adapter = adapterMachine

        @SuppressLint("SetTextI18n")
        binding.spinnerMachine.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    v: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        machineId = listMachineResult[position - 1].deviceId!!
                    }
                    println("machineId : $machineId")
                }
            }

        val listSpinnerMicon: MutableList<String> = ArrayList()
        listSpinnerMicon.add("-Pilih-")
        val listMiconsult: MutableList<ResultsItem> = ArrayList()

        for (element in listResult) {
            if (element.type == "Micon") {
                listSpinnerMicon.add(element.name!!)
                listMiconsult.add(element)
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, listSpinnerMicon)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerMicon.adapter = adapter

            @SuppressLint("SetTextI18n")
            binding.spinnerMicon.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        v: View?,
                        p: Int,
                        id: Long
                    ) {
                        if (listResult.isEmpty()) {
                            myToast("Data tidak ditemukan, silahkan kembali ke halaman utama")
                        } else {
                            if (p != 0) {
                                miconId = listMiconsult[p - 1].deviceId!!
                            }

                        }
                    }
                }

            @SuppressLint("SetTextI18n")
            binding.spinnerTimeDuration.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        v: View?,
                        p: Int,
                        id: Long
                    ) {
                        timeDuration = parent?.getItemAtPosition(p).toString()
                    }
                }

            @SuppressLint("SetTextI18n")
            binding.spinnerPulse.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        v: View?,
                        p: Int,
                        id: Long
                    ) {
                        pulse = parent?.getItemAtPosition(p).toString()
                    }
                }

            @SuppressLint("SetTextI18n")
            binding.spinnerPulseDuration.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        v: View?,
                        p: Int,
                        id: Long
                    ) {
                        pulseDurationType = parent?.getItemAtPosition(p).toString()

                    }
                }


        }
    }
}

