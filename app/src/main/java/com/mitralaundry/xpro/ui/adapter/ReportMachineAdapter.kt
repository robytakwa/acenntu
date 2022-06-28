package com.mitralaundry.xpro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.data.model.ReportMesin
import com.mitralaundry.xpro.databinding.ItemListReportMachineBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class ReportMachineAdapter : RecyclerView.Adapter<ReportMachineAdapter.ViewHolder>() {

    private var models: MutableList<ReportMesin> = ArrayList()
    var modelListData: MutableList<ReportMesin> = models

    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemListReportMachineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item, position,)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData(data: List<ReportMesin>) {
        models.addAll(data)
        notifyDataSetChanged()

    }

    fun clearData() {
        models.clear()
        notifyDataSetChanged()

    }

    inner class ViewHolder(private val itemBinding: ItemListReportMachineBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: ReportMesin, position: Int) {
            itemBinding.tvCycle.text = model.counter
            itemBinding.tvMachine.text = model.name
            itemBinding.tvPrice.text =rupiahFormat(model.currentPrice)
            itemBinding.tvTotal.text = rupiahFormat( model.pendapatan.toString())
            itemBinding.tvDate.text = formatDate("yyyy-MM-dd", "dd MMMM yyyy", model.createdAt)
            itemBinding.btnCard.setOnClickListener { onItemClickListener!!.onItemClick(position) }

        }
    }

    private fun rupiahFormat(number: String?) : String {
        val temp = when {
            number!!.contains(".") -> ceil(number.toDouble()).toInt().toString()
            number.isEmpty() -> "0"
            else -> number
        }
        return "Rp ${NumberFormat.getIntegerInstance(Locale.GERMANY).format(temp.toLong())}"
    }


    fun formatDate(inputPattern: String?, outputPattern: String?, date: String?): String? = SimpleDateFormat(outputPattern!!, Locale("id", "ID")).format(
        SimpleDateFormat(inputPattern!!, Locale("id", "ID")).parse(date!!)!!)


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun SetOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


}