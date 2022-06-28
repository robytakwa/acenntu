package com.mitralaundry.xpro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.data.model.ReportLaundry
import com.mitralaundry.xpro.databinding.ItemReportTopupBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class ReportTopUpAdapter : RecyclerView.Adapter<ReportTopUpAdapter.ViewHolder>() {
    private var models: MutableList<ReportLaundry> = ArrayList()
    var modelListData: MutableList<ReportLaundry> = models

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        ItemReportTopupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData(data: List<ReportLaundry>) {
        models.addAll(data)
        notifyDataSetChanged()

    }

    fun clearData() {
        models.clear()
        notifyDataSetChanged()

    }

    inner class ViewHolder(private val itemBinding: ItemReportTopupBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: ReportLaundry, position: Int) {
            itemBinding.tvMember.text = model.memberId
            itemBinding.tvName.text = model.name
            itemBinding.tvTopup.text = rupiahFormat(model.topup.toString())

            itemBinding.tvDate.text = formatDate("yyyy-MM-dd", "dd MMMM yyyy", model.createdDate)


        }

        private fun rupiahFormat(number: String?): String {
            val temp = when {
                number!!.contains(".") -> ceil(number.toDouble()).toInt().toString()
                number.isEmpty() -> "0"
                else -> number
            }
            return "Rp ${NumberFormat.getIntegerInstance(Locale.GERMANY).format(temp.toLong())}"
        }


        fun formatDate(inputPattern: String?, outputPattern: String?, date: String?): String? =
            SimpleDateFormat(outputPattern!!, Locale("id", "ID")).format(
                SimpleDateFormat(inputPattern!!, Locale("id", "ID")).parse(date!!)!!
            )

    }
}