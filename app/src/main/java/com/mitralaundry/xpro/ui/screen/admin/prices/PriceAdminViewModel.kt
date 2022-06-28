package com.mitralaundry.xpro.ui.screen.admin.prices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.response.ResponsePriceAdmin
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.PriceAdminRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PriceAdminViewModel @Inject constructor( private val repository : PriceAdminRepository) : ViewModel() {
    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private val _statusSearch = MutableLiveData<Status>()
    val statusSearch: LiveData<Status> = _statusSearch

    private val _data = MutableLiveData<List<ResponsePriceAdmin>>()
    val data = _data

    private val _dataSearch = MutableLiveData<List<ResponsePriceAdmin>>()
    val dataSearch = _dataSearch


    fun getPriceAdmin() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getPriceListAdmin()
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<ResponsePriceAdmin>>(Gson().toJson(result.results))
                    _data.value = newResult.data
                    _status.value = Status.SUCCESS

                }
                _status.value = Status.NONE
            } catch (throwable: Throwable) {
                _status.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun getPriceAdminSearch(name : String) {
        _statusSearch.value = Status.LOADING

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getPriceListSearch(name)
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<ResponsePriceAdmin>>(Gson().toJson(result.results))
                    _dataSearch.value = newResult.data
                    _statusSearch.value = Status.SUCCESS


                }
            } catch (throwable: Throwable) {
                println(throwable.message)
            }
        }
    }
}