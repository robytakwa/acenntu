package com.mitralaundry.xpro.ui.screen.admin.merchant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.response.Merchant
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.MerchantRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ViewModelMerchant @Inject constructor(val repository: MerchantRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _data = MutableLiveData<List<Merchant>>()
    val data: LiveData<List<Merchant>> = _data

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    init {
        getAllMerchant()
    }

    fun getAllMerchant() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = repository.getAllMerchant(1, 50, "", "", "")
                if (result.code == 200) {
                    val resultt =
                        GsonUtil.fromJson<ResultData<Merchant>>(Gson().toJson(result.results))
                    _data.value = resultt.data
                    _status.value = Status.SUCCESS
                } else if (result.code == 401) {
                    _error.value = result.description
                    _status.value = Status.ERROR
                }
            } catch (throwable: Throwable) {
                _status.value = Status.ERROR
                println("view model")
                println(throwable.message)
                when (throwable) {
                    is IOException -> {
                        _error.value = "Network Error"
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        _error.value = throwable.message()
                        if (code == 401) {
                            println("--------401----------")
                        }
                    }
                    else -> {
                        _error.value = "Unknown Error"
                        println(throwable.message)
                    }
                }
            }
        }
    }
}