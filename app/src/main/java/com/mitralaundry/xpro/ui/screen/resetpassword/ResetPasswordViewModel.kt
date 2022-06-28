package com.mitralaundry.xpro.ui.screen.resetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitralaundry.xpro.data.model.ResetPasswordRequest
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.network.api
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor() : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>  = _status



    fun resetPass(email: String, password: String, confirm :String) {
        val reset = ResetPasswordRequest(email = email, password = password, confirmPassword =confirm )
        resetPassword(reset)
    }

    private fun resetPassword(resetPasswordRequest: ResetPasswordRequest) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    api.resetPassword(resetPasswordRequest)
                }
                if (result.code == 200) {
                    if (result.description.isNotEmpty()) {
                        _status.value = Status.ERROR
                        _message.value = result.description
                    }
                    _status.value = Status.SUCCESS
                }
                println(result)
            } catch (e: Exception) {
                _status.value = Status.ERROR
                println("error")
                println(e)

            }
        }

    }
}