package com.mitralaundry.xpro.ui.screen.merchant.profilemerchant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.UpdateProfileRequest
import com.mitralaundry.xpro.data.model.response.ResponseProfile
import com.mitralaundry.xpro.data.model.response.ResultUser
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.ProfileRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
 class ProfileMerchantViewModel @Inject constructor (private val repository: ProfileRepository) :
    ViewModel() {

    private val _statusProfile = MutableLiveData<Status>()
    val statusProfile: LiveData<Status> = _statusProfile

    private val _statusUpdateMerchant = MutableLiveData<Status>()
    val statusUpdateMerchant: LiveData<Status> = _statusUpdateMerchant


    private val _dataProfile = MutableLiveData<ResponseProfile?>()
    val dataProfile = _dataProfile




    fun getDataProfileMerchant() {
        _statusProfile.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getProfileMerchant()
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResponseProfile>(Gson().toJson(result.results))
                    _dataProfile.value = newResult
                    _statusProfile.value = Status.SUCCESS

                }
                _statusProfile.value = Status.NONE
            } catch (throwable: Throwable) {
                _statusProfile.value = Status.ERROR
                println(throwable.message)
            }
        }
    }


    fun updateProfileMerchant(userId : Int, name : String, email : String, phone : String, old_password : String,
                              new_password : String, confirmPassword : String) {
        _statusUpdateMerchant.value = Status.LOADING
        viewModelScope.launch {
            try {

                val updateProfileMerchantRequest = UpdateProfileRequest(
                    userId = userId,
                    name = name,
                    email = email,
                    phone = phone,
                    oldPassword = old_password,
                    password = new_password,
                    passwordConfirmation = confirmPassword
                )

                val result = withContext(Dispatchers.IO) {
                    repository.updateProfileMerchant(updateProfileMerchantRequest)
                }


                if (result.code == 201) {
                    val newResult =
                        GsonUtil.fromJson<ResultUser>(Gson().toJson(result.results))
                    _statusUpdateMerchant.value = Status.SUCCESS
                    repository.saveSession(newResult.accessToken.toString())

                } else {
                    _statusUpdateMerchant.value = Status.ERROR
                }


            } catch (throwable: Throwable) {
                _statusUpdateMerchant.value = Status.ERROR
                println(throwable.message)
            }
        }
    }



}
