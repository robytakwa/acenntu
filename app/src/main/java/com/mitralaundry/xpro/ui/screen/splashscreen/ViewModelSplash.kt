package com.mitralaundry.xpro.ui.screen.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitralaundry.xpro.data.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSplash @Inject constructor(
    private val splashRepository: SplashRepository
) : ViewModel() {

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

//    init {
//        _isLogin.value = false
//    }

    fun check() {
        viewModelScope.launch {
            _isLogin.value = splashRepository.isLogin()
        }
    }

    fun finishNavigateToHome() {
        _isLogin.value = false
    }

}