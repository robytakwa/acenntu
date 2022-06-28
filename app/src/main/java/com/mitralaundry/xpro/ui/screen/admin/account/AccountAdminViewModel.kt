package com.mitralaundry.xpro.ui.screen.admin.account

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.AccountAdminRequest
import com.mitralaundry.xpro.data.model.ResetUserAdminRequest
import com.mitralaundry.xpro.data.model.response.DataUser
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.ResultUser
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.AccountAdminRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AccountAdminViewModel @Inject constructor(private val repository: AccountAdminRepository) : ViewModel(){

    private val _statusList = MutableLiveData<Status>()
    val statusList: LiveData<Status> = _statusList

    private val _statusReset = MutableLiveData<Status>()
    val statusReset: LiveData<Status> = _statusReset


    private val _dataList = MutableLiveData<List<DataUser>>()
    val dataList = _dataList

    private val _lastPage = MutableLiveData<Int>()
    val lastPackage = _lastPage


    private val _statusSearch = MutableLiveData<Status>()
    val statusSearch: LiveData<Status> = _statusSearch


    private val _dataListSearch = MutableLiveData<List<DataUser>>()
    val dataListSearch = _dataListSearch

    private val _statusCreate = MutableLiveData<Status>()
    val statusCreate: LiveData<Status> = _statusCreate


    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    private val _isErrorName = MutableLiveData<Boolean>()
    val isErrorName = _isErrorName

    private var _isErrorPhoneNumber = MutableLiveData<Boolean>()
    val isErrorPhoneNumber = _isErrorPhoneNumber

    private val _isErrorEmail = MutableLiveData<Boolean>()
    val isErrorEmail = _isErrorEmail

    private val _errorName = MutableLiveData<String>()
    val errorName = _errorName

    private val _errorPhoneNumber = MutableLiveData<String>()
    val errorPhoneNumber = _errorPhoneNumber

    private val _errorEmail = MutableLiveData<String>()
    val errorEmail = _errorEmail


     fun getListAccountAdmin() {

         _statusList.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {

                    repository.getListAccountAdmin()
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<DataUser>>(Gson().toJson(result.results))
                    _dataList.value = newResult.data
                    _lastPage.value = newResult.lastPage
                    _statusList.value = Status.SUCCESS

                }
            } catch (throwable: Throwable) {
                _statusList.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun getListAccountAdminSearch(name : String) {
        _statusSearch.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getListAccountAdminSearch(name)
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<DataUser>>(Gson().toJson(result.results))
                    _dataListSearch.value = newResult.data
                    _statusSearch.value = Status.SUCCESS

                }
            } catch (throwable: Throwable) {
                _statusSearch.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun createAccount(context: Context) {
        _statusCreate.value = Status.LOADING
        viewModelScope.launch {
            try {
                val accountAdminRequest = AccountAdminRequest(

                    name = name.value.toString(),
                    email = email.value.toString(),
                    phone = phone.value.toString()
                )


                when {
                    name.value.isNullOrBlank() -> {
                        Toast.makeText(
                            context,
                            "Data Tidak boleh kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    phone.value.isNullOrBlank() -> {
                        Toast.makeText(
                            context,
                            "Data Tidak boleh kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    email.value.isNullOrBlank() -> {
                        Toast.makeText(
                            context,
                            "Data Tidak boleh kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                    phone.value.toString().length < 10 -> {
                        Toast.makeText(
                            context,
                            "Nomor telpon tidak boleh kurang dari 10",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.toString())
                        .matches() -> {
                        Toast.makeText(
                            context,
                            "Format email tidak valid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val result = withContext(Dispatchers.IO) {
                            repository.createAccountAdmin(accountAdminRequest)
                        }

                        if (result.code == 201) {
                            _statusCreate.value = Status.SUCCESS
                            val newResult =
                                GsonUtil.fromJson<ResultUser>(Gson().toJson(result.results))

                            _statusCreate.value = Status.SUCCESS
                            repository.saveSession(newResult.accessToken.toString())


                        }
                    }
                }
            } catch (throwable: Throwable) {
                _statusCreate.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun resetUserAdmin(userId: String) {
        _statusReset.value = Status.LOADING
        viewModelScope.launch {
            try {
                val resetUserAdminRequest = ResetUserAdminRequest(
                    userId = userId
                )

                val result = withContext(Dispatchers.IO) {
                    repository.resetUserAdmin(resetUserAdminRequest)
                }
                if (result.code == 201) {
                    val newResult =
                        GsonUtil.fromJson<ResultUser>(Gson().toJson(result.results))


                    _statusReset.value = Status.SUCCESS
                    repository.saveSession(newResult.accessToken.toString())


                }
            } catch (throwable: Throwable) {
                _statusReset.value = Status.ERROR
                println(throwable.message)
            }
        }
    }



    fun setEmail(newEmail: String) {
        if (newEmail.isNotEmpty()) {
            isErrorEmail.value = false
        } else if (newEmail.length >= 4) {
            _isErrorEmail.value = false
        }
        email.value = newEmail
    }

    fun setName(newName: String) {
        if (newName.isNotEmpty()) {
            _isErrorName.value = false
        }
        name.value = newName
    }

    fun setPhoneNumber(newPhoneNumber: String) {
        if (newPhoneNumber.isNotEmpty()) {
            _isErrorPhoneNumber.value = false
        } else if (newPhoneNumber.length >= 10) {
            _isErrorPhoneNumber.value = false
        }
        phone.value = newPhoneNumber
    }


}