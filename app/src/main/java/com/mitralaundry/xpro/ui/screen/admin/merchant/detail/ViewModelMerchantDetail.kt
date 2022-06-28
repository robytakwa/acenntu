package com.mitralaundry.xpro.ui.screen.admin.merchant.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.response.Merchant
import com.mitralaundry.xpro.data.model.response.Outlet
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.MerchantRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMerchantDetail @Inject constructor(val repository: MerchantRepository) :
    ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private val _data = MutableLiveData<Merchant?>()
    val data: LiveData<Merchant?> = _data


    private val _outlets = MutableLiveData<MutableList<Outlet>>()
    val outlet: LiveData<MutableList<Outlet>> = _outlets

    fun setStatus(status: Status) {
        _status.value = status
    }

    fun getMerchantDetail(merchantId: String?) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            merchantId?.let {
                try {
                    val result = repository.merchantDetail(merchantId);
                    println(result)
                    if (result.code == 200) {
                        val resultConvert =
                            GsonUtil.fromJson<Merchant>(Gson().toJson(result.results))
                        _data.value = resultConvert
                        _status.value = Status.SUCCESS
                    }
                } catch (throwable: Throwable) {
                    _status.value = Status.ERROR
                }
            }
        }
    }
}