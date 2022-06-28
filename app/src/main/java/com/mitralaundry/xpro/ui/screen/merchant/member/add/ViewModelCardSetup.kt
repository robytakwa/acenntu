package com.mitralaundry.xpro.ui.screen.merchant.member.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mitralaundry.xpro.data.model.Member
import com.mitralaundry.xpro.data.model.response.Status
import javax.inject.Inject

class ViewModelCardSetup @Inject constructor() : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status = _status

    private val _member = MutableLiveData<Member>()
    val member = _member

    private val _isErrorName = MutableLiveData<Boolean>()
    val isErrorName = _isErrorName

    private var _isErrorPhoneNumber = MutableLiveData<Boolean>()
    val isErrorPhoneNumber = _isErrorPhoneNumber

    private val _isErrorSaldo = MutableLiveData<Boolean>()
    val isErrorSaldo = _isErrorSaldo

    private val _name = MutableLiveData<String>()
    val name = _name

    private val _errorName = MutableLiveData<String>()
    val errorName = _errorName

    private val _errorPhoneNumber = MutableLiveData<String>()
    val errorPhoneNumber = _errorPhoneNumber

    private val _errorSaldo = MutableLiveData<String>()
    val errorSaldo = _errorSaldo

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber = _phoneNumber

    private val _saldo = MutableLiveData<String>()
    val saldo = _saldo

    private val _outletId = MutableLiveData<String>()
    val outletId = _outletId

    fun setOutletId(newOutletId: String) {
        _outletId.value = newOutletId
    }

    fun setSaldo(newSaldo: String) {
        if (newSaldo.isNotEmpty()) {
            _isErrorSaldo.value = false
        } else if (newSaldo.length >= 4) {
            _isErrorSaldo.value = false
        }
        _saldo.value = newSaldo
    }

    fun setName(newName: String) {
        if (newName.isNotEmpty()) {
            _isErrorName.value = false
        }
        _name.value = newName
    }

    fun setPhoneNumber(newPhoneNumber: String) {
        if (newPhoneNumber.isNotEmpty()) {
            _isErrorPhoneNumber.value = false
        } else if (newPhoneNumber.length >= 10) {
            _isErrorPhoneNumber.value = false
        }
        _phoneNumber.value = newPhoneNumber
    }

    fun setStatus(newStatus : Status) {
        status.value = newStatus
    }

    fun proses() {
        var saldoInt = 0
        if (saldo.value?.isNotEmpty() == true) {
            saldoInt = saldo.value.toString().trim().filter { it.isDigit() }.toInt()
        }
        when {
            name.value.isNullOrBlank() -> {
                _isErrorName.value = true
                _errorName.value = "Name empty"
            }
            phoneNumber.value.isNullOrBlank() -> {
                _isErrorPhoneNumber.value = true
                _errorPhoneNumber.value = "Phone number empty"
            }
            phoneNumber.value.toString().length < 10 -> {
                _isErrorPhoneNumber.value = true
                _errorPhoneNumber.value = "Nomor telpon tidak boleh kurang dari 10"
            }
            saldo.value.isNullOrBlank() -> {
                _isErrorSaldo.value = true
                _errorSaldo.value = "Saldo masih 0"
            }
            saldoInt < 5000 -> {
                _isErrorSaldo.value = true
                _errorSaldo.value = "Saldo tidak boleh lebih kecil dari 5000"
            }
            else -> {
                _status.value = Status.SUCCESS
                _member.value = Member(
                    name = name.value.toString(),
                    phone = phoneNumber.value.toString(),
                    saldo = saldoInt,
                    keyCard = "",
                    outletId = outletId.value.toString(),
                    memberId = "",
                )
            }
        }
    }
}