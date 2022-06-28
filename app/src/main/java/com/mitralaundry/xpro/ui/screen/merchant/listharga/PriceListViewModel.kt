package com.mitralaundry.xpro.ui.screen.merchant.listharga

import android.content.Context
import android.nfc.TagLostException
import android.nfc.tech.MifareClassic
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.UpdatePriceRequest
import com.mitralaundry.xpro.data.model.response.ResponsePrice
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.model.response.ResultUpdatePrice
import com.mitralaundry.xpro.data.repository.DetailPriceRepository
import com.mitralaundry.xpro.util.Constant
import com.mitralaundry.xpro.util.Constant.CODE_PRICE
import com.mitralaundry.xpro.util.GsonUtil
import com.mitralaundry.xpro.util.MifareUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PriceListViewModel @Inject constructor(
    private val repository: DetailPriceRepository
) : ViewModel() {
    private val _statusList = MutableLiveData<Status>()
    val statusList: LiveData<Status> = _statusList

    private val _statusUpdate = MutableLiveData<Status>()
    val statusUpdate: LiveData<Status> = _statusUpdate

    private val _outletId = MutableLiveData<String>()
    val outletId = _outletId

    private val _name = MutableLiveData<String>()
    val name: MutableLiveData<String> = _name

    private val _price = MutableLiveData<String>()
    val price: LiveData<String> = _price

    private val _data = MutableLiveData<List<ResponsePrice>>()
    val data = _data

    private lateinit var mifare: MifareClassic

    private val _textInfo = MutableLiveData<String>()
    val textInfo: LiveData<String> = _textInfo

    private val _disableButton = MutableLiveData<Boolean>()
    val disableButton: LiveData<Boolean> = _disableButton

    private val _statusCard = MutableLiveData<Status>()
    val statusCard = _statusCard

    private val _errorPrice = MutableLiveData<String>()
    val errorPrice = _errorPrice

    private val _merchantId = MutableLiveData<String>()
    val merchantId = _merchantId

    val isErrorPrice = MutableLiveData<Boolean>()

    fun setOutletId(newOutletId: String) {
        _outletId.value = newOutletId
    }

    fun getPriceList() {
        _statusList.value = Status.LOADING
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getPriceList(outletId.value.toString())
                }
                if (result.code == 200) {
                    val newResult =
                        GsonUtil.fromJson<List<ResponsePrice>>(Gson().toJson(result.results))
                    _data.value = newResult
                    _statusList.value = Status.SUCCESS

                }
                _statusList.value = Status.NONE
            } catch (throwable: Throwable) {
                _statusList.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun updatePrice(mappingId: Int, context: Context) {
        println("update price")
        _statusUpdate.value = Status.LOADING
        viewModelScope.launch {
            try {
                when {
                    price.value.isNullOrBlank() -> {
                        _errorPrice.value = "Data Tidak boleh kosong"
                        Toast.makeText(
                            context,
                            "Data Tidak boleh kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        val updatePriceRequest = UpdatePriceRequest(
                            mappingId = mappingId,
                            wDPrice = price.value.toString()
                        )
                        val result = repository.updatePrice(updatePriceRequest)

                        if (result.code == 201) {
                            _statusUpdate.value = Status.SUCCESS
                            val newResult =
                                GsonUtil.fromJson<ResultUpdatePrice>(Gson().toJson(result.results))

                            // set merchant id for card
                            _merchantId.value = newResult.merchantId

                            repository.saveSession(newResult.accessToken)
                        }
                    }
                }
            } catch (throwable: Throwable) {
                _statusUpdate.value = Status.ERROR
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

    fun writeDataToCard() {
        try {
            MifareUtils.writeBlock(mifare, blockIndex = Constant.BLOCK_CODE, CODE_PRICE)
            MifareUtils.writeBlock(mifare, blockIndex = Constant.BLOCK_UPDATE_PRICE, price.value.toString())
            MifareUtils.writeBlock(mifare, blockIndex = Constant.BLOCK_MERCHANT_ID, merchantId.value.toString())
            _textInfo.value = "Data berhasil ditambahkan ke kartu"
            statusCard.value = Status.SUCCESS
            MifareUtils.closeCard(mifare)
        } catch (e: TagLostException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
            _disableButton.value = false
        } catch (e: IOException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
            _disableButton.value = false
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
            _disableButton.value = false
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
            _disableButton.value = false
        }
    }

    fun setPrice(newName: String?) {
        if (newName?.isNotEmpty() == true) {
            isErrorPrice.value = false
        }
        _price.value = newName
    }

    fun setMerchantId(merchantId: String?) {
       _merchantId.value = merchantId
    }
}