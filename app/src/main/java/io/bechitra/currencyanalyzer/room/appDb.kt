package io.bechitra.currencyanalyzer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.bechitra.currencyanalyzer.room.dao.CurrencyDao
import io.bechitra.currencyanalyzer.room.entity.Currency
import io.bechitra.currencyanalyzer.room.entity.currencysettings

@Database(entities = [currencysettings::class,Currency::class],version = 1)
abstract class appDb : RoomDatabase() {

    abstract fun currencydao() : CurrencyDao
}