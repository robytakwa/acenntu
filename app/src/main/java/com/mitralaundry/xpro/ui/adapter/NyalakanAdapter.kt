//package com.souttab.laundry.ui.adapter
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.view.View
//import android.view.ViewGroup
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.google.gson.Gson
//import com.souttab.laundry.R
//import com.mitralaundry.xpro.BaseListAdapter
//import com.mitralaundry.xpro.Results
//import java.util.concurrent.TimeUnit
//
//
//class NyalakanAdapter : BaseListAdapter<Results, NyalakanAdapter.ViewHolder>() {
//
//    val itemView2: View? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(getView(parent, R.layout.item_mapping_update)!!)
//    }
//
//    override fun getBindViewHolder(holder: ViewHolder, position: Int, data: Results) {
//        holder.bindData(data, context!!, position)
//        holder.itemView.setOnClickListener { v ->
//            if (clickListener != null) {
//                clickListener?.onItemClick(position, v, holder)
//            }
//        }
//          }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bindData(data: Results, context: Context, position: Int) {
//            val listSpinner: MutableList<String> = ArrayList()
//            listSpinner.add("Pilih Mesin")
//            for (element in data.data!!) if (element != null) {
//                listSpinner.add(element.machine!!.toString())
//            }
//
//            val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, listSpinner)
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//            itemView.spinner_list_pilih_device.adapter = adapter
//            itemView.tv_list_type_baru_device.text = data.machineType
//            when {
//                data.machineType.equals("wash", true) -> { setView("Mulai Mencuci", R.drawable.ic_button_cuci) }
//                data.machineType.equals("dry", true) -> { setView("Mulai Mengeringkan", R.drawable.ic_button_kering) }
//                else -> { setView("Mulai Proses", R.drawable.ic_rounded_background_blue) }
//            }
//
//            @SuppressLint("SetTextI18n")
//            itemView.spinner_list_pilih_device.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                override fun onNothingSelected(parent: AdapterView<*>?) {}
//                override fun onItemSelected(parent: AdapterView<*>?, v: View?, p: Int, id: Long) {
//                    if (p > 0) {
//                        Pref.write(Pref.ISSELECTEDMESIN,true)
//
//                        if (data.listDevice[p-1]?.timeDurationSatuan == "ms"){
//                            if (data.listDevice[p-1]?.timeDuration!!.toInt()>1000){
//                                val minutes: Long = TimeUnit.MILLISECONDS.toSeconds(data.listDevice[p-1]?.timeDuration!!.toLong())
//                                itemView.tv_list_estimasi.text = "$minutes detik"
//                            }else{
//                                itemView.tv_list_estimasi.text = data.listDevice[p-1]?.timeDuration!! + " milidetik"
//                            }
//                        }else if (data.listDevice[p-1]?.timeDurationSatuan == "s"){
//                            if (data.listDevice[p-1]?.timeDuration!!.toInt()>60){
//                                val minutes: Long = TimeUnit.SECONDS.toMinutes(data.listDevice[p-1]?.timeDuration!!.toLong())
//                                itemView.tv_list_estimasi.text = "$minutes menit"
//                            }else{
//                                itemView.tv_list_estimasi.text = data.listDevice[p-1]?.timeDuration!! + " detik"
//                            }
//                        }else if (data.listDevice[p-1]?.timeDurationSatuan == "m"){
//                            if (data.listDevice[p-1]?.timeDuration!!.toInt()>60){
//                                val minutes: Long = TimeUnit.MINUTES.toHours(data.listDevice[p-1]?.timeDuration!!.toLong())
//                                itemView.tv_list_estimasi.text = "$minutes Jam"
//                            }else{
//                                itemView.tv_list_estimasi.text = data.listDevice[p-1]?.timeDuration!! + " menit"
//                            }
//                        }else if (data.listDevice[p-1]?.timeDurationSatuan == "h"){
//                            if (data.listDevice[p-1]?.timeDuration!!.toInt()>24){
//                                val minutes: Long = TimeUnit.HOURS.toDays(data.listDevice[p-1]?.timeDuration!!.toLong())
//                                itemView.tv_list_estimasi.text = "$minutes hari"
//                            }else{
//                                itemView.tv_list_estimasi.text = data.listDevice[p-1]?.timeDuration!! + " jam"
//                            }
//                        }else{
//                            itemView.tv_list_estimasi.text = data.listDevice[p-1]?.timeDuration!!
//                        }
//                        Pref.write(Pref.SELECTEDMESIN, Gson().toJson(data.listDevice[p-1]))
//                    }else{
////                        itemView.tv_list_type_baru_device.text = ""
//                        itemView.tv_list_estimasi.text = ""
//                        Pref.write(Pref.SELECTEDMESIN,"")
//                        Pref.write(Pref.ISSELECTEDMESIN,false)
//                    }
//                    Pref.write(Pref.POSITIONCARDM,position)
//                }
//            }
//        }
//
//        private fun setView(text: String, background: Int) {
//            itemView.btn_list_nyalakan_mesins.text = text
//            itemView.btn_list_nyalakan_mesins.setBackgroundResource(background)
//        }
//    }
//}