package com.mitralaundry.xpro.ui.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.Login
import com.mitralaundry.xpro.data.model.SessionUser
import com.mitralaundry.xpro.data.model.User
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.network.api
import com.mitralaundry.xpro.data.repository.UserLoginRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: UserLoginRepository) : ViewModel() {

    private val _successLogin = MutableLiveData<Boolean>()
    val successLogin: LiveData<Boolean> get() = _successLogin

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> get() = _status

    private val _isActive = MutableLiveData<String>()
    val isActive = _isActive
    private val _email = MutableLiveData<String>()
    val email = _email


    fun succesNavLogin() {
        _successLogin.value = false
    }

    fun setStatus() {
        _status.value = Status.NONE
        _successLogin.value = false
    }

    fun login(email: String, password: String) {
        val user = User(email = email, password = password)
        postLogin(user)
    }

    private fun postLogin(user: User) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = api.login(user)
                if (result.code == 200) {
                    _successLogin.value = true
                    if (result.description.isNotEmpty()) {
                        _status.value = Status.ERROR
                        _message.value = result.description
                        val newResult =
                            GsonUtil.fromJson<Login>(Gson().toJson(result.result))
                        _isActive.value = newResult.isActive
                        _email.value = newResult.email

                    }
                    val login = result.result
                    val session = SessionUser(
                        role = login.role,
                        token = login.accessToken,
                        isLogin = true,
                        userName = login.name
                    )

                    repository.saveSession(session)
                    _status.value = Status.SUCCESS
                }
                println(result)
            } catch (e: Exception) {
                _status.value = Status.ERROR
                println("error")
                println(e)
                _successLogin.value = false
            }
        }

    }
}