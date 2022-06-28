package com.mitralaundry.xpro.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitralaundry.xpro.data.model.SessionUser
import com.mitralaundry.xpro.data.model.response.ItemOutlet
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.HomeRepository
import com.mitralaundry.xpro.di.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val dataStore: DataStoreManager,
    private val repository: HomeRepository
) : ViewModel() {

    private val _user = MutableLiveData<SessionUser>()
    val user: LiveData<SessionUser> = _user

    private val _status = MutableLiveData<Status>()
    val status = _status

    private val _outlet = MutableLiveData<List<ItemOutlet>>()
    val outlet = _outlet

    private val _outletId = MutableLiveData<String>()
    val outletId = _outletId

    private val _outletName = MutableLiveData<String>()
    val outletName = _outletName

    private val _logout = MutableLiveData<Boolean>()
    val logout = _logout

    init {
        viewModelScope.launch {
            _status.value = Status.LOADING
            _user.value = dataStore.fetchInitialPreferences()

            if (!user.value?.role.contentEquals("administrator")) {
                val result = repository.getListOutletDataStore()
                _outlet.value = result

                result.map { itemOutlet ->
                    if (itemOutlet.selected) {
                        _outletName.value = itemOutlet.name
                        _outletId.value = itemOutlet.outletId
                        _status.value = Status.SUCCESS
                    }
                }
            }
        }
    }

    fun setOutletId(newOutletId: String) {
        _outletId.value = newOutletId
        setOutletSelected(newOutletId)

        // save outlet id selected to session
        viewModelScope.launch {
            repository.saveOutlet(newOutletId)
        }
    }

    fun setOutletName(newOutletName: String) {
        _outletName.value = newOutletName
    }

    fun logout() {
        viewModelScope.launch {
            dataStore.deleteAllSession()
            repository.deleteOutlet()
            _logout.value = true
        }
    }

    private fun setOutletSelected(outletId: String) {
        println("update selected item")
        viewModelScope.launch {
            repository.updateSelected(outletId)
        }
    }
}