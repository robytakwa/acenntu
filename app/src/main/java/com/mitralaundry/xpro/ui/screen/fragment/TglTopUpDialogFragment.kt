package com.mitralaundry.xpro.ui.screen.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.databinding.DialogTanggalBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class TglTopUpDialogFragment(private var hitApi: () -> Unit) : DialogFragment() {

    private lateinit var binding: DialogTanggalBinding
    val myCalendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_tanggal, container, false)



        initListener()
        return binding.root
    }

    private fun updateLabel() {
//        val myFormat = "MM/dd/yy"
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.etTanggalStart.setText(dateFormat.format(myCalendar.time))
    }

    private fun updateLabelEnd() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.etDialogTanggalEnd.setText(dateFormat.format(myCalendar.time))
    }

    private fun initListener() {
        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }

        val date2 =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabelEnd()
            }

        binding.etTanggalStart.setOnClickListener {
            DatePickerDialog(
                requireContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        binding.etDialogTanggalEnd.setOnClickListener {
            DatePickerDialog(
                requireContext(), date2, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnDialogTanggalCancel.setOnClickListener {
            dismiss()
        }

        binding.btnReset.setOnClickListener {
            binding.etTanggalStart.text.clear()
            binding.etDialogTanggalEnd.text.clear()
        }

        binding.btnTerapkan.setOnClickListener {
            val dialogListener = activity as DialogListener
            dialogListener.listLoadByDate(  binding.etTanggalStart.text.toString(), binding.etDialogTanggalEnd.text.toString())

            hitApi().also {

                this.dismiss()
            }

        }



    }

    interface DialogListener {
        fun listLoadByDate(
            dateStart: String, dateEnd: String
        )
    }


}
