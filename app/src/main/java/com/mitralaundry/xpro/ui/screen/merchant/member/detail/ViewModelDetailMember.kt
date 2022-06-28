package com.mitralaundry.xpro.ui.screen.merchant.member.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.Member
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDetailMember @Inject constructor(val repository: DetailMemberRepository) :
    ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status = _status

    private val _user = MutableLiveData<Member?>()
    val user = _user


    fun getDetail(idMember: String) {
        viewModelScope.launch {
            _status.value = Status.LOADING
            val result = repository.getDetailMember(idMember = idMember)
            if (result.code == 200) {
                val newResult =
                    GsonUtil.fromJson<Member>(Gson().toJson(result.results))
                _user.value = newResult
            }
            println(result)
        }
    }
}