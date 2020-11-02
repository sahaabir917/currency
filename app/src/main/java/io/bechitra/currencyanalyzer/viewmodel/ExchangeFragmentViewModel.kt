package io.bechitra.currencyanalyzer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.bechitra.currencyanalyzer.DataRepository
import io.bechitra.currencyanalyzer.data.Currency
import io.bechitra.currencyanalyzer.data.DataProcessor
import kotlin.properties.Delegates

class ExchangeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DataRepository = DataRepository(application)
    fun getDataSet():Collection<Currency>{
        return DataProcessor().getCurrencyData()
    }

    fun requestNetwork(): MutableLiveData<List<io.bechitra.currencyanalyzer.network.Currency>> {
        return repository.getResponse
    }
}