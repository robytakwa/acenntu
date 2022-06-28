package com.mitralaundry.xpro.ui.screen.merchant.laporanmesin

import android.nfc.TagLostException
import android.nfc.tech.MifareClassic
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.ReportMesin
import com.mitralaundry.xpro.data.model.UploadLaporanMesinRequest
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.ResultMember
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.ReportMesinRepository
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
class ViewModelReportMesin @Inject constructor(val repository: ReportMesinRepository) :
    ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status = _status



    private val _statusFilter = MutableLiveData<Status>()
    val statusFilter = _statusFilter

    private val _statusUpload = MutableLiveData<Status>()
    val statusUpload = _statusUpload


    private val _listReport = MutableLiveData<List<ReportMesin>>()
    val data = _listReport

    private val _listReport2 = MutableLiveData<List<ReportMesin>>()
    val dataFilter = _listReport2

    private lateinit var mifare: MifareClassic

    private val _textInfo = MutableLiveData<String>()
    val textInfo: LiveData<String> = _textInfo

    private val _disableButton = MutableLiveData<Boolean>()
    val disableButton: LiveData<Boolean> = _disableButton





    fun getReportFilter(startdate: String, enddate: String, outletID: String) {
        _statusFilter.value = Status.LOADING

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getReportMesinFilter(startdate,enddate, outletID)

                }
                if (result.code == 200) {

                    val newResult =
                        GsonUtil.fromJson<ResultData<ReportMesin>>(Gson().toJson(result.results))
                    _listReport2.value = newResult.data
                    _statusFilter.value = Status.SUCCESS
                    println("data saya : ${newResult.data}")
                }
            } catch (throwable: Throwable) {
                _statusFilter.value = Status.ERROR
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


    fun uploadLaporan(outletID: String) {
        _statusUpload.value = Status.LOADING
        viewModelScope.launch {
            try {
               //TODO ini Datannya masih di hardcode mas @andy
                val uploadLaporanMesinRequest = UploadLaporanMesinRequest(
                    outletId = outletID,
                    counter = 1200,
                    currPrice = "20000",
                    type = "DRY MACHINE",
                    mac = "A8:5A:44:20:3Z:V7"

                )
                val result = repository.uploadLaporanMesin(uploadLaporanMesinRequest)
                if (result.code == 201) {
                    _statusUpload.value = Status.SUCCESS
                    val newResult =
                        GsonUtil.fromJson<ResultMember>(Gson().toJson(result.results))
                    writeDataToCard(outletID)

                    repository.saveSession(newResult.accessToken)
                }
                println(result)
            } catch (throwable: Throwable) {
                _statusUpload.value = Status.ERROR
                println(throwable.message)
            }
        }
    }


    private fun writeDataToCard(outletID: String) {
        try {
            MifareUtils.writeBlock(mifare, blockIndex = Constant.BLOCK_CODE, Constant.CODE_USER)
            MifareUtils.writeBlock(
                mifare,
                blockIndex = Constant.BLOCK_MERCHANT_ID,
                outletID
            )


            _textInfo.value = "Data berhasil ditambahkan ke kartu"
            MifareUtils.closeCard(mifare)

        } catch (e: TagLostException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IOException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        }
    }



}
