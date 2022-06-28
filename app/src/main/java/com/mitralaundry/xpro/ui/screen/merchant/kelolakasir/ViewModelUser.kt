package com.mitralaundry.xpro.ui.screen.merchant.kelolakasir

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.ResetUserRequest
import com.mitralaundry.xpro.data.model.UserAccountRequest
import com.mitralaundry.xpro.data.model.response.*
import com.mitralaundry.xpro.data.repository.UserRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelUser @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {


    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private val _statusSearch = MutableLiveData<Status>()
    val statusSearch: LiveData<Status> = _statusSearch

    private val _statusAdd = MutableLiveData<Status>()
    val statusAdd: LiveData<Status> = _statusAdd


    private val _statusReset = MutableLiveData<Status>()
    val statusReset: LiveData<Status> = _statusReset


    private val _data = MutableLiveData<List<DataUser>>()
    val data = _data

    private val _dataUser = MutableLiveData<List<UserResponse>>()
    val dataUser = _dataUser

    private val _dataSearch = MutableLiveData<List<DataUser>>()
    val dataSearch = _dataSearch

    private val _outletId = MutableLiveData<String>()
    val outletId: LiveData<String> = _outletId

    val name = MutableLiveData<String>()

    private val _email = MutableLiveData<String>()
    val email = _email

    private val _phone = MutableLiveData<String>()
    val phone = _phone

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


    fun getListUser() {
        viewModelScope.launch {
            try {
                val result = repository.getUser()
                println(result)
                    val newResult =
                        GsonUtil.fromJson<List<UserResponse>>(Gson().toJson(result))
                _dataUser.value = newResult

            } catch (throwable: Throwable) {

                println(throwable.message)
            }
        }
    }


     fun listUser() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getListUser()
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<DataUser>>(Gson().toJson(result.results))

                    _data.value = newResult.data
                    _status.value = Status.SUCCESS

                }
            } catch (throwable: Throwable) {
                _status.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun listUserSearch(name: String) {
        _statusSearch.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getListUserSearch(name)
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<DataUser>>(Gson().toJson(result.results))

                    _dataSearch.value = newResult.data
                    _statusSearch.value = Status.SUCCESS

                }
            } catch (throwable: Throwable) {
                _statusSearch.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun addUser(outletId: String, context:Context) {
        _statusAdd.value = Status.LOADING
        viewModelScope.launch {
            try {
                val userAccountRequest = UserAccountRequest(
                    outletId = outletId,
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

                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches() -> {
                        Toast.makeText(
                            context,
                            "Format email tidak valid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val result = withContext(Dispatchers.IO) {
                            repository.addUser(userAccountRequest)
                        }
                            if (result.code == 201) {
                                _statusAdd.value = Status.SUCCESS

                                val newResult =
                                    GsonUtil.fromJson<ResultUser>(Gson().toJson(result.results))
                                repository.saveSession(newResult.accessToken.toString())
                            }

                    }

                }
            } catch (throwable: Throwable) {
                _statusAdd.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun resetUser(outletId: String,userId: String) {
        _statusReset.value = Status.LOADING
        viewModelScope.launch {
            try {
                val resetUserRequest = ResetUserRequest(
                    outletId = outletId,
                    userId = userId
                )

                val result = withContext(Dispatchers.IO) {
                    repository.resetUser(resetUserRequest)
                }
                if (result.code == 201) {
                    _statusReset.value = Status.SUCCESS
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
        _email.value = newEmail
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
        _phone.value = newPhoneNumber
    }

}