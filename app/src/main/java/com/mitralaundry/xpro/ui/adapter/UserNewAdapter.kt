package com.mitralaundry.xpro.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.data.model.response.DataUser
import com.mitralaundry.xpro.data.model.response.UserResponse
import com.mitralaundry.xpro.databinding.ItemListAccountBinding
import com.mitralaundry.xpro.databinding.ItemListUserBinding
import kotlinx.coroutines.withContext

class UserNewAdapter : RecyclerView.Adapter<UserNewAdapter.IViewHolder>() {
    private var models: MutableList<UserResponse> = ArrayList()
    var modelListData: MutableList<UserResponse> = models
    var context: Context? = null


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

    inner class IViewHolder(private val itemBinding: ItemListUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: UserResponse, position: Int,  context: Context) {
            itemBinding.tvUserName.text = model.login

            Glide.with(context)
                .load(model.avatarUrl)
                .into(itemBinding.ivUser)


        }
    }

}
