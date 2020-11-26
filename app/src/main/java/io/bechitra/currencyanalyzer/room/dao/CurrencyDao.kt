package io.bechitra.currencyanalyzer.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.bechitra.currencyanalyzer.room.entity.Currency
import io.bechitra.currencyanalyzer.room.entity.currencysettings

@Dao
interface CurrencyDao {

    @Insert
    fun insertCurrencySettings(currencysettings: currencysettings?)

    @Update
    fun updatesettings(currencysettings: currencysettings?)

    @Delete
    fun deletesettings(currencysettings: currencysettings?)

    @Query("SELECT * FROM currencysettings")
    fun getAllSettings() : List<currencysettings>
}