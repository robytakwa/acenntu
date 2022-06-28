package com.mitralaundry.xpro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.data.model.response.DataItem
import com.mitralaundry.xpro.databinding.ItemListMappingBinding
import java.util.ArrayList

class MappingListAdapter : RecyclerView.Adapter<MappingListAdapter.ViewHolder>() {
    private var models : MutableList<DataItem> = ArrayList()
    var modelListData : MutableList<DataItem> = models
    private var onItemEditClickListener: OnItemClickListener? =null
    private var onItemCardClickListener: OnItemCardClickListener? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemListMappingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData (data :List<DataItem>) {
        models.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemListMappingBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind (model: DataItem, position: Int) {
            itemBinding.tvType.text = model.machine!!.type
            itemBinding.tvName.text = model.machine.name
            itemBinding.tvSn.text = model.machine.serialNum

            itemBinding.tvType2.text = model.micon!!.type
            itemBinding.tvName2.text = model.micon.name
            itemBinding.tvSn2.text = model.micon.serialNum
            itemBinding.tvMac.text = model.micon.mac
            itemBinding.tvIp.text = model.micon.address

            itemBinding.tvPulse.text = model.pulse
            itemBinding.tvPulseDuration.text = model.pulseDur + model.pulseDurType
            itemBinding.tvTimeDuration.text = model.timeDur + model.timeDurType

            itemBinding.btnEdit.setOnClickListener {
                onItemEditClickListener!!.onItemClick(position) }

            itemBinding.btnCard.setOnClickListener {
                onItemCardClickListener!!.onItemCardClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemCardClickListener {
        fun onItemCardClick(position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemEditClickListener = onItemClickListener
    }

    fun setOnItemCardClickListener(onItemClickListener: OnItemCardClickListener?) {
        this.onItemCardClickListener = onItemClickListener
    }
}