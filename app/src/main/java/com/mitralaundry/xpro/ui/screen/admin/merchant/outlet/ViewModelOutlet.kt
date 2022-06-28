package com.mitralaundry.xpro.ui.screen.admin.merchant.outlet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.response.ResultOutlet
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.MerchantRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelOutlet @Inject constructor(private val repository: MerchantRepository) :
    ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status = _status

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _name = MutableLiveData<String>()
    val name = _name

    private val _phone = MutableLiveData<String>()
    val phone = _phone

    private val _address = MutableLiveData<String>()
    val address = _address

    private val _description = MutableLiveData<String>()
    val description = _description

    fun setName(name: String) {
        _name.value = name
    }

    fun setPhone(phone: String) {
        _phone.value = phone
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setDescription(description: String) {
        _description.value = description
    }

    fun updateOutlet(outletId: String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            val result = repository.updateOutlet(
                outletId = outletId,
                name = name.value.toString(),
                phone = phone.value.toString(),
                address = address.value.toString(),
                description = description.value.toString()
            )
            println(result)
            if (result.code == 201) {
                _status.value = Status.SUCCESS
                val newResult =
                    GsonUtil.fromJson<ResultOutlet>(Gson().toJson(result.results))
                repository.saveNewToken(newResult.accessToken)
                _message.value = result.description
            }
        }
    }

    fun saveOutlet(merchanId: String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = repository.addOutlet(
                    merchantId = merchanId,
                    name = name.value.toString(),
                    phone = phone.value.toString(),
                    address = address.value.toString(),
                    description = description.value.toString()
                )
                println(result)
                if (result.code == 201) {
                    _status.value = Status.SUCCESS
                    val newResult =
                        GsonUtil.fromJson<ResultOutlet>(Gson().toJson(result.results))
                    repository.saveNewToken(newResult.accessToken)
                    _message.value = result.description
                } else if (result.code == 400) {
                    _message.value = result.description
                    _status.value = Status.ERROR
                } else if (result.code == 401) {
                    _message.value = result.description
                    _status.value = Status.ERROR
                }
            } catch (throwable: Throwable) {
                println(throwable.message)
            }
        }
    }

    fun getDetailOutlet(outletId: String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = repository.getDetailOutlet(outletId = outletId)
                println("-get detail outlet---")
                println(result)
                if (result.code == 200) {
                    _status.value = Status.NONE
                    val data =
                        GsonUtil.fromJson<ResultOutlet>(Gson().toJson(result.results))
                    println("----- data ")
                    println(data)
                    _name.value = data.name
                    _phone.value = data.phone
                    _address.value = data.address
                    _description.value = data.description
                }
            } catch (throwable: Throwable) {
                _status.value = Status.ERROR
                println(throwable.message)
            }
        }
    }
}