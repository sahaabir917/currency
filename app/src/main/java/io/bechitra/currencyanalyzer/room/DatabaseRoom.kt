package io.bechitra.currencyanalyzer.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.bechitra.currencyanalyzer.room.dao.CurrencyDao
import io.bechitra.currencyanalyzer.room.entity.Currency
import io.bechitra.currencyanalyzer.room.entity.currencysettings

@Database(entities = [Currency::class,currencysettings::class], version = 2, exportSchema = false)
public abstract class DatabaseRoom : RoomDatabase(){
    abstract fun currencyDao() : CurrencyDao

    companion object {
        @Volatile
        private var INSTANCE : DatabaseRoom? = null

        fun getDatabase(context: Context) : DatabaseRoom {
            val tempInstance = INSTANCE

            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseRoom::class.java,
                    "local_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}