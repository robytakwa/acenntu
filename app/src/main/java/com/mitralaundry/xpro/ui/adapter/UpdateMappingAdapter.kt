package com.mitralaundry.xpro.ui.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.data.model.response.DataItem
import com.mitralaundry.xpro.databinding.ItemMappingUpdateBinding
import kotlin.collections.ArrayList

class UpdateMappingAdapter : RecyclerView.Adapter<UpdateMappingAdapter.ViewHolder>() {
    private var models : MutableList<DataItem> = ArrayList()
    var modelListData : MutableList<DataItem> = models
    var context: Context? = null
    private var onItemEditClickListener: MappingListAdapter.OnItemClickListener? =null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int) = ViewHolder (
        ItemMappingUpdateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item, context!!,  position)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData (data :List<DataItem>, context2: Context) {
        context = context2
        models.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemMappingUpdateBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: DataItem, context: Context, position: Int) {
            val listSpinner: MutableList<String> = ArrayList()
            listSpinner.add("-Pilih-")
            for (element in listOf(model.machine)) {
                listSpinner.add(model.machine?.name.toString())
                println("model saya : ${model.machine?.name.toString()}")
            }

            val adapter: ArrayAdapter<String> = ArrayAdapter(context, R.layout.simple_spinner_item, listSpinner)
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            itemBinding.spinnerMachine.adapter = adapter

        }

    }
}