package com.mitralaundry.xpro.ui.screen.merchant.laporantopup

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
class ViewModelReportTopup @Inject constructor(val repository: ReportTopupRepository) :
    ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status = _status

    private val _listReport = MutableLiveData<List<ReportLaundry>>()
    val data = _listReport


     fun getReport(startDate: String, endDate: String, outletId: String) {
         _status.value = Status.LOADING

         viewModelScope.launch {

            val result = repository.getAllReportTopup(startDate, endDate, outletId)
            val newResult =
                GsonUtil.fromJson<ResultData<ReportLaundry>>(Gson().toJson(result.results))
            _listReport.value = newResult.data
             _status.value = Status.SUCCESS

             println(newResult)
        }
    }

}
