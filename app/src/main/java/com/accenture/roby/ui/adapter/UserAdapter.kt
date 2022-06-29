package com.accenture.roby.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.accenture.roby.data.model.response.UserResponse
import com.mitralaundry.xpro.databinding.ItemListUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.IViewHolder>(), Filterable {
    private var models: MutableList<UserResponse> = ArrayList()
    var modelListData: MutableList<UserResponse> = models
    var context: Context? = null
    private var onItemClickListener: OnItemClickListener? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IViewHolder (
        ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        val item = modelListData[position]
        holder.bind(item, position, context!!)
    }

    override fun getItemCount(): Int = modelListData.size

    fun setData(data:List <UserResponse>, _context: Context) {
        models.addAll(data)
        notifyDataSetChanged()
        context = _context

    }

    fun clearData() {
        models.clear()
        notifyDataSetChanged()

    }
    inner class IViewHolder(private val itemBinding: ItemListUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: UserResponse, position: Int,  context: Context) {
            itemBinding.tvUserName.text = model.login

            Glide.with(context)
                .load(model.avatarUrl)
                .into(itemBinding.ivUser)

            itemView.setOnClickListener { onItemClickListener!!.onItemClick(position) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun SetOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: MutableList<UserResponse> = ArrayList()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(modelListData)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                    for (item in modelListData) {
                        if (item.login!!.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                modelListData.clear()
                modelListData.addAll(results?.values as List<UserResponse>)
                notifyDataSetChanged()
            }
        }
    }
}
