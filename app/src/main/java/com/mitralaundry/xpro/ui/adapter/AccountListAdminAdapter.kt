package com.mitralaundry.xpro.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitralaundry.xpro.data.model.response.DataUser
import com.mitralaundry.xpro.databinding.ItemListAccountBinding

class AccountListAdminAdapter : RecyclerView.Adapter<AccountListAdminAdapter.IViewHolder>() {
    private var models: MutableList<DataUser> = ArrayList()
    var modelListData: MutableList<DataUser> = models
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = IViewHolder(
        ItemListAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData(data: List<DataUser>) {
        models.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        models.clear()
        notifyDataSetChanged()

    }


    inner class IViewHolder(private val itemBinding: ItemListAccountBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: DataUser, position: Int) {
            if (model.isAdmin == "1") {
                itemBinding.tvAdmin.text = "Ya"
            } else {
                itemBinding.tvAdmin.text = "Tidak"
            }

            itemBinding.tvNameValue.text = model.name
            itemBinding.tvEmailValue.text = model.email
            itemView.setOnClickListener {
                onItemClickListener!!.onItemClick(position)

            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun SetOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


}