package io.bechitra.currencyanalyzer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.bechitra.currencyanalyzer.room.RoomAppDb
import io.bechitra.currencyanalyzer.room.entity.currencysettings

class SettingFragmentViewModel(app:Application):AndroidViewModel(app) {
    lateinit var allsettings: MutableLiveData<List<currencysettings>>

    init {
        allsettings = MutableLiveData()
        getAllSettings()
    }

    val settingsDao = RoomAppDb.getAppDatabase(getApplication())?.currencydao()

    fun getAllsettingsObserver(): MutableLiveData<List<currencysettings>> {
      getAllSettings()
        return allsettings
    }

    fun getAllSettings() {
        val list = settingsDao?.getAllSettings()
        allsettings.postValue(list)
    }

    fun insertSettingsInfo(settings: currencysettings) {
        settingsDao?.insertCurrencySettings(settings)
        getAllSettings()
    }

    fun DeleteSettingsInfo(settings: currencysettings) {
        settingsDao?.deletesettings(settings)
        getAllSettings()
    }


    fun UpdateSettingsInfo(settings: currencysettings) {
        settingsDao?.updatesettings(settings)
        getAllSettings()
    }


}

