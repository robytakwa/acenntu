package com.mitralaundry.xpro.ui.screen.merchant.member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.response.ResultMember
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.MemberRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelMember @Inject constructor(private val repository: MemberRepository) : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status : LiveData<Status> = _status

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _data = MutableLiveData<List<ResultMember>>()
    val data = _data

    private val _outletId = MutableLiveData<String>()
    val outletId = _outletId


    private val _merchantId = MutableLiveData<String>()
    val merchantId = _merchantId

    fun setOutletId(newOutletId: String) {
        _outletId.value = newOutletId
    }

    fun getListMember() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getListMember(outletId.value.toString())
                }
//                val result = repository.getListMember(outletId.value.toString())
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<ResultMember>>(Gson().toJson(result.results))
                    _data.value = newResult.data
                    _status.value = Status.SUCCESS
                    if (newResult.data.size > 0) {
                        _merchantId.value = newResult.data[0].merchantId
                    }
                }
            } catch (throwable: Throwable) {
                _status.value = Status.ERROR
                println("error : ${throwable.message}")
            }
        }
    }

    fun addMember() {
        viewModelScope.launch {
        }
    }

}