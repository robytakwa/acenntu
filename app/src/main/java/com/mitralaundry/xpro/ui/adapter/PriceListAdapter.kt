package com.mitralaundry.xpro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.data.model.response.ResponsePrice
import com.mitralaundry.xpro.databinding.ItemListPriceBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class PriceListAdapter : RecyclerView.Adapter<PriceListAdapter.ViewHolder>() {

    private var models: MutableList<ResponsePrice> = ArrayList()
    var modelListData: MutableList<ResponsePrice> = models
    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemListPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData(data: List<ResponsePrice>) {
        models.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemListPriceBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: ResponsePrice, position: Int) {
            println("data price 2 :" + model)
            itemBinding.tvMachineValue.text = model.name
            itemBinding.tvPriceValue.text = rupiahFormat(model.wDPrice)
            itemBinding.tvDateValue.text = formatDate("yyyy-MM-dd", "dd MMMM yyyy", model.createdAt)
            itemView.setOnClickListener { onItemClickListener!!.onItemClick(position) }
        }
    }

    fun formatDate(inputPattern: String?, outputPattern: String?, date: String?): String? =
        SimpleDateFormat(outputPattern!!, Locale("id", "ID")).format(
            SimpleDateFormat(inputPattern!!, Locale("id", "ID")).parse(date!!)!!
        )

    fun rupiahFormat(number: String?): String {
        val temp = when {
            number!!.contains(".") -> ceil(number.toDouble()).toInt().toString()
            number.isEmpty() -> "0"
            else -> number
        }
        return "Rp ${NumberFormat.getIntegerInstance(Locale.GERMANY).format(temp.toLong())}"
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun SetOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

}