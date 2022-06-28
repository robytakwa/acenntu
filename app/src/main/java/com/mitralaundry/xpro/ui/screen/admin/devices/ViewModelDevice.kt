package com.mitralaundry.xpro.ui.screen.admin.devices

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.model.CreateDeviceRequest
import com.mitralaundry.xpro.data.model.Device
import com.mitralaundry.xpro.data.model.response.ResultDevice
import com.mitralaundry.xpro.data.repository.DevicesRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDevice @Inject constructor(private val repository: DevicesRepository) : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status = _status

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _data = MutableLiveData<List<Device>>()
    val data = _data

    private val _type = MutableLiveData<String>()
    val type = _type

    private val _name = MutableLiveData<String>()
    val name = _name

    private val _serialNumber = MutableLiveData<String>()
    val serialNumber = _serialNumber

    private val _address = MutableLiveData<String>()
    val address = _address

    private val _statusMachine = MutableLiveData<String>()
    val statusMachine = _statusMachine

    private val _isActive = MutableLiveData<Boolean>()
    val isActive = _isActive

    private val _mac = MutableLiveData<String>()
    val mac = _mac

    private val _deviceId = MutableLiveData<String>()
    val deviceId = _deviceId

    fun setStatusAktif(status: String) {
        _statusMachine.value = status
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setType(type: String) {
        _type.value = type
    }

    fun setAddress(address: String) {
        _address.value = address
    }

    fun setSerialNumber(serialNumber: String) {
        _serialNumber.value = serialNumber
    }

    fun setActive(isActive: Boolean) {
        _isActive.value = isActive
    }

    fun setMac(mac: String) {
        _mac.value = mac
    }

    init {
        getListDevices()
    }

    fun getListDevices() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = repository.getListDevices()
                println(result)
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<Device>>(Gson().toJson(result.results))
                    _data.value = newResult.data
                }
            } catch (throwable: Throwable) {
                _status.value = Status.ERROR
                println(throwable.message)
            }
        }
    }


    fun saveDevice(context: Context) {

        _status.value = Status.LOADING
        viewModelScope.launch {
            val createDeviceRequest = CreateDeviceRequest(
                type = type.value.toString(),
                name = name.value.toString(),
                serialNum = serialNumber.value.toString(),
                address = address.value.toString(),
                status =  "Aktif",
                isActive = true,
                mac = mac.value.toString(),
            )


            when {
                name.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                type.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                serialNumber.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                address.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                mac.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val result = repository.addDevice(createDeviceRequest);

                    if (result.code == 201) {
                        val newResult =
                            GsonUtil.fromJson<ResultDevice>(Gson().toJson(result.results))

                        _status.value = Status.SUCCESS
                        repository.saveSession(newResult.accessToken)

                    } else {
                        _status.value = Status.ERROR

                    }

                }
            }

            }

        }


    fun getDetailDevice(deviceId: String) {
        _deviceId.value = deviceId
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = repository.detailDevice(deviceId)
                if (result.code == 200) {

                    val resultConvert =
                        GsonUtil.fromJson<ResultDevice>(Gson().toJson(result.results))

                    _name.value = resultConvert.name
                    _serialNumber.value = resultConvert.serialNumber
                    _statusMachine.value = resultConvert.status
                    _mac.value = resultConvert.mac ?: ""
                    _address.value = resultConvert.address ?: ""
                    _isActive.value = resultConvert.isActive
                    _type.value = resultConvert.type

                    println("seteleh di assign value")
                    println(name.value.toString())
                }
            } catch (throwable: Throwable) {
                println(throwable.message)
                _status.value = Status.ERROR
            }
        }
    }

    fun updateDevice(context: Context) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            val createDeviceRequest = CreateDeviceRequest(
                type = type.value.toString(),
                name = name.value.toString(),
                serialNum = serialNumber.value.toString(),
                address = address.value.toString(),
                status =  "Aktif",
                isActive = true,
                mac = mac.value.toString(),
                deviceId = deviceId.value.toString()
            )


            when {
                name.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                type.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                serialNumber.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                address.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                mac.value.isNullOrBlank() -> {
                    Toast.makeText(
                        context,
                        "Data Tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val result = repository.updateDevice(createDeviceRequest);

                    if (result.code == 201) {
                        val newResult =
                            GsonUtil.fromJson<ResultDevice>(Gson().toJson(result.results))

                        _status.value = Status.SUCCESS
                        repository.saveSession(newResult.accessToken)

                    } else {
                        _status.value = Status.ERROR

                    }

                }
            }


        }
    }
}