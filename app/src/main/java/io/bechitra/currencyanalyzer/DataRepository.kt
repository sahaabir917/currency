package io.bechitra.currencyanalyzer

import android.app.Application
import android.util.Log
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import io.bechitra.currencyanalyzer.network.Api
import io.bechitra.currencyanalyzer.network.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DataRepository(private val application: Application) {
    /*val getDataSet : LiveData<List<Currency>> = currencyDao.getCurrencyData()

    suspend fun insert(currency: Currency) {
        currencyDao.insert(currency)
    }

    suspend fun update(currency: Currency) {
        currencyDao.update(currency)
    }

    suspend fun delete(currency: Currency) {
        currencyDao.delete(currency)
    }

    suspend fun reset() {
        currencyDao.reset()
    }
    */



    private val response = MutableLiveData<List<Currency>>()

    private var coroutineJob = Job()
    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    init {
        getNetworkService()
    }

    private fun getNetworkService(){
        coroutineScope.launch {
//            d("pages",pagenumber.toString())

            var getPropertyDeffered = Api.retrofitService.getPropertyAsync(50,1)

            try {
                var property = getPropertyDeffered.await()
                response.value = property.data
            } catch (t:Throwable) {
                Log.d("response", "Failed.............. :)")
            }
        }
    }

    val getResponse : MutableLiveData<List<Currency>>
        get() = response


}