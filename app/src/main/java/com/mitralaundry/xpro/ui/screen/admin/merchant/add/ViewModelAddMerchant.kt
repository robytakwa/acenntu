package com.mitralaundry.xpro.ui.screen.admin.merchant.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.Merchant
import com.mitralaundry.xpro.data.model.response.ResultMerchant
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.MerchantRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAddMerchant @Inject constructor(val repository: MerchantRepository) : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status = _status

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage

    private val _merchantName = MutableLiveData<String>()
    val merchantName = _merchantName

    private val _merchantPhone = MutableLiveData<String>()
    val merchantPhone = _merchantPhone

    private val _merchantAddress = MutableLiveData<String>()
    val merchantAddress = _merchantAddress

    private val _merchantDescription = MutableLiveData<String>()
    val merchantDescription = _merchantDescription

    private val _outletName = MutableLiveData<String>()
    val outletName = _outletName

    private val _outletPhone = MutableLiveData<String>()
    val outletPhone = _outletPhone

    private val _outletAddress = MutableLiveData<String>()
    val outletAddress = _outletAddress

    private val _outletDescription = MutableLiveData<String>()
    val outletDescription = _outletDescription

    private val _picName = MutableLiveData<String>()
    val picName = _picName

    private val _picPhone = MutableLiveData<String>()
    val picPhone = _picPhone

    private val _picEmail = MutableLiveData<String>()
    val picEmail = _picEmail


    fun setMerchanName(merchantNameUpdate: String) {
        _merchantName.value = merchantNameUpdate
    }

    fun setMerchanPhone(merchantPhoneUpdate: String) {
        _merchantPhone.value = merchantPhoneUpdate
    }

    fun setMerchanAddress(merchantAddressUpdate: String) {
        _merchantAddress.value = merchantAddressUpdate
    }

    fun setMerchanDescription(merchantDescriptionUpdate: String) {
        _merchantDescription.value = merchantDescriptionUpdate
    }

    fun setOutletName(outletNameUpdate: String) {
        _outletName.value = outletNameUpdate
    }

    fun setOutletPhone(outletPhoneUpdate: String) {
        _outletPhone.value = outletPhoneUpdate
    }

    fun setOutletAddress(outletAddressUpdate: String) {
        _outletAddress.value = outletAddressUpdate
    }

    fun setOutletDescription(outletDescriptionUpdate: String) {
        _outletDescription.value = outletDescriptionUpdate
    }

    fun setPicName(picNameUpdate: String) {
        _picName.value = picNameUpdate
    }

    fun setPicPhone(picPhoneUpdate: String) {
        _picPhone.value = picPhoneUpdate
    }

    fun setPicEmail(picEmailUpdate: String) {
        _picEmail.value = picEmailUpdate
    }

    fun saveMerchant() {
        val merchant = Merchant(
            picUserName = picName.value.toString(),
            picUserEmail = picEmail.value.toString(),
            picUserPhone = picPhone.value.toString(),
            merchantName = merchantName.value.toString(),
            merchantPhone = merchantPhone.value.toString(),
            merchantAddress = merchantAddress.value.toString(),
            merchantDescription = merchantDescription.value.toString(),
            outletName = outletName.value.toString(),
            outletPhone = outletPhone.value.toString(),
            outletAddress = outletAddress.value.toString(),
            outletDescription = outletDescription.value.toString()
        )
        viewModelScope.launch {
            _status.value = Status.LOADING
            try {
                val result = repository.addMerchant(merchant)
                if (result.code == 201) {
                    _status.value = Status.SUCCESS
                    val newResult =
                        GsonUtil.fromJson<ResultMerchant>(Gson().toJson(result.results))
                    repository.saveNewToken(newResult.accessToken)
                    _errorMessage.value = result.description
                } else if (result.code == 400) {
                    _status.value = Status.ERROR
                    _errorMessage.value = result.description
                } else if (result.code == 401) {
                    _status.value = Status.ERROR
                    _errorMessage.value = result.description
                }
            } catch (throwable: Throwable) {
                println(throwable.message)
            }
        }
    }
}