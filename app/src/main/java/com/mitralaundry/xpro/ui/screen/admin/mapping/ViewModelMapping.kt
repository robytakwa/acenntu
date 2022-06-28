package com.mitralaundry.xpro.ui.screen.admin.mapping

import android.content.Context
import android.nfc.TagLostException
import android.nfc.tech.MifareClassic
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.CreateMappingRequest
import com.mitralaundry.xpro.data.model.Device
import com.mitralaundry.xpro.data.model.UpdateMappingRequest
import com.mitralaundry.xpro.data.model.response.*
import com.mitralaundry.xpro.data.repository.MappingRepository
import com.mitralaundry.xpro.util.Constant.BLOCK_BRAND
import com.mitralaundry.xpro.util.Constant.BLOCK_CODE
import com.mitralaundry.xpro.util.Constant.BLOCK_DURATION
import com.mitralaundry.xpro.util.Constant.BLOCK_DURATION_COUNTER
import com.mitralaundry.xpro.util.Constant.BLOCK_MERCHANT_ID
import com.mitralaundry.xpro.util.Constant.BLOCK_ONLINE
import com.mitralaundry.xpro.util.Constant.BLOCK_PULSE
import com.mitralaundry.xpro.util.Constant.BLOCK_TITLE
import com.mitralaundry.xpro.util.Constant.BLOCK_TYPE_MACHINE
import com.mitralaundry.xpro.util.Constant.BLOCK_UPDATE_PRICE
import com.mitralaundry.xpro.util.Constant.CODE_MASTER
import com.mitralaundry.xpro.util.GsonUtil
import com.mitralaundry.xpro.util.MifareUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ViewModelMapping @Inject constructor(private val repository: MappingRepository) :
    ViewModel() {

    private val _statusList = MutableLiveData<Status>()
    val statusList: LiveData<Status> = _statusList

    private val _statusDevice = MutableLiveData<Status>()
    val statusDevice: LiveData<Status> = _statusDevice

    private val _dataDevice = MutableLiveData<List<ResultsItem>>()
    val dataDevice = _dataDevice

    private val _statusUpdate = MutableLiveData<Status>()
    val statusUpdate: LiveData<Status> = _statusUpdate

    private val _statusCreate = MutableLiveData<Status>()
    val statusCreate: LiveData<Status> = _statusCreate

    private val _message = MutableLiveData<String>()
    val message = _message

    private val _dataList = MutableLiveData<List<DataItem>>()
    val dataList = _dataList

    private val _pulse = MutableLiveData<String>()
    val pulse: LiveData<String> = _pulse

    private lateinit var mifare: MifareClassic


    fun getListDevicesMapping() {
        _statusDevice.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = repository.getListDevicesMapping()
                println(result)
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<List<ResultsItem>>(Gson().toJson(result.results!!))
                    _dataDevice.value = newResult
                    _statusDevice.value = Status.SUCCESS

                }
            } catch (throwable: Throwable) {
                _statusDevice.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun getListMapping(outletId: String) {
        _statusList.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = repository.getListMapping(1, "", outletId)
                println("-------------- raw")
                println(result)

                val resultConvert =
                    GsonUtil.fromJson<List<DataItem>>(Gson().toJson(result.results!!.data))
                _dataList.value = resultConvert
                _statusList.value = Status.SUCCESS

                println(" ------------ result ----------")
                println(resultConvert)

            } catch (throwable: Throwable) {
                println("---error")
                println(throwable.message)
            }
        }
    }

    fun updateMapping(
        mappingId: Int,
        outletId: String,
        machineId: Int,
        miconId: Int,
        pulse: String,
        pulseDurType: String,
        pulseDur: Int,
        timeDurType: String,
        timeDur: Int,
        price: Int,
        status: String,
        isActive: Boolean,
        reportOnline: Boolean,
    ) {
        // todo add loading on screen
        _statusUpdate.value = Status.LOADING
        viewModelScope.launch {
            try {
                val updateMappingRequest = UpdateMappingRequest(
                    mappingId = mappingId,
                    outletId = outletId,
                    machineId = machineId,
                    miconId = miconId,
                    pulse = pulse,
                    pulseDurType = pulseDurType,
                    pulseDur = pulseDur,
                    timeDurType = timeDurType,
                    timeDur = timeDur,
                    price = price,
                    status = status,
                    isActive = isActive,
                    reportOnline = reportOnline
                )
                val result = repository.updateMapping(updateMappingRequest)
                if (result.code == 201) {
                    _statusUpdate.value = Status.SUCCESS
                    val newResult =
                        GsonUtil.fromJson<ResultUpdatePrice>(Gson().toJson(result.results))
                    repository.saveSession(newResult.accessToken)
                }
                println(result)
            } catch (throwable: Throwable) {
                _statusUpdate.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun addMifare(mifareClassic: MifareClassic, context: Context) {
        mifare = mifareClassic
        connectCard(context)
    }

    private fun connectCard(context: Context) {
        try {
            if (!mifare.isConnected) {
                mifare.connect()
                Toast.makeText(
                    context,
                    "Kartu terdeteksi, silakan klik ok untuk diproses",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: IOException) {
            Toast.makeText(
                context,
                "Tidak ada kartu terdeteksi",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    fun writeDataMappingtoCard(context: Context, position: Int) {
        try {
            val readPulse = dataList.value?.get(position)?.pulse.toString()
            val title = dataList.value?.get(position)?.name.toString()
            val price = dataList.value?.get(position)?.price?.split(".")!![0]
            val typeMachine = dataList.value?.get(position)?.machine?.type.toString()
            val merchantId = dataList.value?.get(position)?.merchantId.toString()
            val durationPulse = dataList.value?.get(position)?.pulseDur.toString()
            val durationCounter = dataList.value?.get(position)?.timeDur.toString()
            val online = dataList.value?.get(position)?.reportOnline.toString()

            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_CODE, CODE_MASTER)
            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_PULSE, readPulse)
            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_TITLE, title)
            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_UPDATE_PRICE, price)
            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_DURATION, durationPulse)
            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_TYPE_MACHINE, typeMachine)
            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_MERCHANT_ID, merchantId)
            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_DURATION_COUNTER, durationCounter)
            MifareUtils.writeBlock(mifare, blockIndex = BLOCK_ONLINE, online)

            Toast.makeText(
                context,
                "Data berhasil ditambahkan ke kartu",
                Toast.LENGTH_SHORT
            ).show()
            MifareUtils.closeCard(mifare)
        } catch (e: TagLostException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Tidak ada kartu yang terdeteksi, silakan tab kembali",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Tidak ada kartu yang terdeteksi, silakan tab kembali",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Tidak ada kartu yang terdeteksi, silakan tab kembali",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Tidak ada kartu yang terdeteksi, silakan tab kembali",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun createMapping(
        outletId: String,
        machineId: Int,
        miconId: Int,
        keyCard: Any?,
        pulse: String,
        pulseDurType: String,
        pulseDur: Int,
        timeDurType: String,
        timeDur: Int,
        price: Int,
        status: String,
        isActive: Boolean,
        reportOnline: Boolean
    ) {
        _statusCreate.value = Status.LOADING
        viewModelScope.launch {
            try {
                val createMappingRequest = CreateMappingRequest(
                    outletId = outletId,
                    machineId = machineId,
                    miconId = miconId,
                    keyCard = keyCard,
                    pulse = pulse,
                    pulseDurType = pulseDurType,
                    pulseDur = pulseDur,
                    timeDurType = timeDurType,
                    timeDur = timeDur,
                    price = price,
                    status = status,
                    isActive = isActive,
                    reportOnline = reportOnline
                )
                val result = repository.createMapping(createMappingRequest)
                if (result.code == 201) {
                    _statusCreate.value = Status.SUCCESS
                    val newResult =
                        GsonUtil.fromJson<ResultUpdatePrice>(Gson().toJson(result.results))
                    repository.saveSession(newResult.accessToken)
                }
            } catch (throwable: Throwable) {
                _statusCreate.value = Status.ERROR
                println(throwable.message)
            }
        }
    }


}