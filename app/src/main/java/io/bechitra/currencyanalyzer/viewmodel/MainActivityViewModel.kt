package io.bechitra.currencyanalyzer.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData

class MainActivityViewModel : BaseObservable() {
    @get:Bindable
    var data : MutableLiveData<String> = MutableLiveData()
    set(value) {
        field = value
        notifyChange()
    }
}