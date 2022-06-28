package com.mitralaundry.xpro.ui.screen.admin.prices

import android.nfc.TagLostException
import android.nfc.tech.MifareClassic
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.DetailAdminRequest
import com.mitralaundry.xpro.data.model.UpdatePriceRequest
import com.mitralaundry.xpro.data.model.response.ResponsePriceAdminDetail
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.PriceAdminRepository
import com.mitralaundry.xpro.util.Constant
import com.mitralaundry.xpro.util.GsonUtil
import com.mitralaundry.xpro.util.MifareUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PriceAdminDetailViewModel @Inject constructor(private val repository: PriceAdminRepository) :
    ViewModel() {

    private val _statusPriceDetail = MutableLiveData<Status>()
    val statusPriceDetail: LiveData<Status> = _statusPriceDetail

    private val _dataPriceDetail = MutableLiveData<List<ResponsePriceAdminDetail>>()
    val dataPriceDetail = _dataPriceDetail

    private val _outletId = MutableLiveData<String>()
    val outletId: LiveData<String> = _outletId

    private val _statusPriceUpdate = MutableLiveData<Status>()
    val statusPriceUpdate: LiveData<Status> = _statusPriceUpdate



    private val _isErrorName = MutableLiveData<Boolean>()
    val isErrorName = _isErrorName

    private val _price = MutableLiveData<String>()
    val price = _price

    private val _errorName = MutableLiveData<String>()
    val errorName = _errorName

    private val _textInfo = MutableLiveData<String>()
    val textInfo: LiveData<String> = _textInfo

    private val _disableButton = MutableLiveData<Boolean>()
    val disableButton: LiveData<Boolean> = _disableButton

    private val _statusCard = MutableLiveData<Status>()
    val statusCard = _statusCard

    private lateinit var mifare: MifareClassic



    fun setPrice(newName: String) {
        if (newName.isNotEmpty()) {
            _isErrorName.value = false
        }
        _price.value = newName
    }
    fun setOutletId(newOutletId: String) {
        _outletId.value = newOutletId
    }


    fun updatePriceAdmin(mappingId: Int) {
        _statusPriceUpdate.value = Status.LOADING
        viewModelScope.launch {
            try {
                val updatePriceRequest = UpdatePriceRequest(

                    mappingId = mappingId,
                    wDPrice = price.value.toString()
                )


                when {
                    price.value.isNullOrBlank() -> {
                        _isErrorName.value = true
                        _errorName.value = "Data Tidak boleh kosong"
                    }
                        else -> {
                            val result = withContext(Dispatchers.IO) {
                                repository.updatePriceAdmin(updatePriceRequest)
                            }
                            if (result.code == 201)
                            _statusPriceUpdate.value = Status.SUCCESS
                        }
                    }
            } catch (throwable: Throwable) {
                _statusPriceUpdate.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun getPriceAdminDetail(outletId: String) {
        _statusPriceDetail.value = Status.LOADING
        viewModelScope.launch {

            val detailAdminRequest = DetailAdminRequest(
                outletId = outletId,
            )
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getDetailPriceAdmin(detailAdminRequest)
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<ResultData<ResponsePriceAdminDetail>>(Gson().toJson(result.results))
                    _dataPriceDetail.value = newResult.data
                    _statusPriceDetail.value = Status.SUCCESS
                    val result =
                        GsonUtil.fromJson<ResponsePriceAdminDetail>(Gson().toJson(result.results))

                    repository.saveSession(result.accessToken.toString())


                }
                _statusPriceDetail.value = Status.NONE
            } catch (throwable: Throwable) {
                _statusPriceDetail.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun addMifare(mifareClassic: MifareClassic) {
        mifare = mifareClassic
        connectCard()

    }

    private fun connectCard() {
        try {
            if (!mifare.isConnected) {
                mifare.connect()
                _disableButton.value = true
                _textInfo.value = "Kartu terdeteksi, silahkan klik ok untuk proses"
            }
        } catch (e: IOException) {
            _textInfo.value = "Tidak ada kartu terdeteksi"
            _disableButton.value = false
        }
    }

    fun writeDataToCard(machine: String, price: String) {
        try {
            MifareUtils.writeBlock(mifare, blockIndex = Constant.BLOCK_CODE, Constant.CODE_USER)
            MifareUtils.writeBlock(
                mifare,
                blockIndex = Constant.BLOCK_TYPE_MACHINE,
                machine
            )

            MifareUtils.writeBlock(
                mifare,
                blockIndex = Constant.BLOCK_SALDO,
                price

            )



            _textInfo.value = "Data berhasil ditambahkan ke kartu"
            statusCard.value = Status.SUCCESS
            MifareUtils.closeCard(mifare)

        } catch (e: TagLostException) {
            e.printStackTrace()
            _disableButton.value = false
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"

        } catch (e: IOException) {
            e.printStackTrace()
            _disableButton.value = false
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"

        } catch (e: IllegalStateException) {
            e.printStackTrace()
            _disableButton.value = false
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"

        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
            _disableButton.value = false
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"

        }
    }


}