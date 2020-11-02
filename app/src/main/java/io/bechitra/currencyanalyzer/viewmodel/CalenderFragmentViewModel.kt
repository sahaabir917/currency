package io.bechitra.currencyanalyzer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.bechitra.currencyanalyzer.DataRepository
import io.bechitra.currencyanalyzer.network.Currency

class CalenderFragmentViewModel (application: Application): AndroidViewModel(application){
    private var repository: DataRepository = DataRepository(application)

    val getResponse:MutableLiveData<List<Currency>>
        get() = repository.getResponse
}