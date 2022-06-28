package com.mitralaundry.xpro.ui.screen.merchant.reportnitip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.model.ReportLaundry
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.repository.ReportTopupRepository
import com.mitralaundry.xpro.util.GsonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelReportNitip @Inject constructor(val repository: ReportTopupRepository) :
    ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status = _status

    private val _listReport = MutableLiveData<List<ReportLaundry>>()
    val listReport = _listReport

    init {
        getReport()
    }

    private fun getReport() {
        viewModelScope.launch {
            val result = repository.getAllReportTopup("2021-12-29", "2022-01-01", "OID-001")
            val newResult =
                GsonUtil.fromJson<ResultData<ReportLaundry>>(Gson().toJson(result.results))
            _listReport.value = newResult.data
            println(newResult)
        }
    }

}
