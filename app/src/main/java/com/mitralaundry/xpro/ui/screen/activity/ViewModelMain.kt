package com.mitralaundry.xpro.ui.screen.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.response.*
import com.mitralaundry.xpro.data.repository.UserRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _dataUser = MutableLiveData<List<UserResponse>>()
    val dataUser = _dataUser

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


}