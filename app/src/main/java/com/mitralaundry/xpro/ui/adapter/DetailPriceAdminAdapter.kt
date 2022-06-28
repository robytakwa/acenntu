package com.mitralaundry.xpro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.data.model.response.ResponsePriceAdminDetail
import com.mitralaundry.xpro.databinding.ItemPriceListAdminDetailBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class DetailPriceAdminAdapter : RecyclerView.Adapter<DetailPriceAdminAdapter.ViewHolder>(){

    private var models : MutableList<ResponsePriceAdminDetail> = ArrayList()
    var modelListData : MutableList<ResponsePriceAdminDetail> = models
    private var onItemClickListener: OnItemClickListener? =null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder (
        ItemPriceListAdminDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData (data : List<ResponsePriceAdminDetail>) {

        models.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemPriceListAdminDetailBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind (model: ResponsePriceAdminDetail, position: Int) {
            println("data price 2 :"+ model)
            itemBinding.tvMachineValue.text = model.name
            itemBinding.tvPriceValue.text = rupiahFormat(model.wDPrice)
            itemBinding.tvDateValue.text = formatDate("yyyy-MM-dd", "dd MMMM yyyy", model.updatedAt)

            itemView.setOnClickListener { onItemClickListener!!.onItemClick(position) }

        }
    }
    fun formatDate(inputPattern: String?, outputPattern: String?, date: String?): String? = SimpleDateFormat(outputPattern!!, Locale("id", "ID")).format(
        SimpleDateFormat(inputPattern!!, Locale("id", "ID")).parse(date!!)!!)


    fun rupiahFormat(number: String?) : String {
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