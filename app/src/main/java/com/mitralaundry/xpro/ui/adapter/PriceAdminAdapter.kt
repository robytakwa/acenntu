package com.mitralaundry.xpro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.data.model.response.ResponsePriceAdmin
import com.mitralaundry.xpro.databinding.ItemPriceListAdminBinding
import java.util.ArrayList

class PriceAdminAdapter : RecyclerView.Adapter<PriceAdminAdapter.ViewHolder>() {
    private var models: MutableList<ResponsePriceAdmin> = ArrayList()
    var modelListData: MutableList<ResponsePriceAdmin> = models
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPriceListAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData(data: List<ResponsePriceAdmin>) {
        models.addAll(data)
        notifyDataSetChanged()

    }

    fun clearData() {
        models.clear()
        notifyDataSetChanged()

    }

    inner class ViewHolder(private val itemBinding: ItemPriceListAdminBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: ResponsePriceAdmin, position: Int) {
            println("data price  :" + model)
            itemBinding.tvMerchantId.text = model.merchantId
            itemBinding.tvOutletId.text = model.outletId
            itemBinding.tvName.text = model.name

            itemView.setOnClickListener { onItemClickListener!!.onItemClick(position) }

        }


    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun SetOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
}