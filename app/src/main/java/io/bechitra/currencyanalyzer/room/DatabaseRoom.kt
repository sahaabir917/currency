package io.bechitra.currencyanalyzer.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.bechitra.currencyanalyzer.room.dao.CurrencyDao
import io.bechitra.currencyanalyzer.room.entity.Currency
import io.bechitra.currencyanalyzer.room.entity.currencysettings

@Database(entities = [currencysettings::class], version = 2)
abstract class RoomAppDb: RoomDatabase() {


    abstract fun currencydao(): CurrencyDao ?

    companion object {
        private var INSTANCE: RoomAppDb?= null
        val migration_1_2: Migration = object: Migration(1 , 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE currencysettings ADD COLUMN type TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE currencysettings ADD COLUMN destinationcountry TEXT DEFAULT ''")

            }
        }

        fun getAppDatabase(context: Context): RoomAppDb? {

            if(INSTANCE == null ) {

                INSTANCE = Room.databaseBuilder<RoomAppDb>(
                    context.applicationContext, RoomAppDb::class.java, "AppDBB"
                )

                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}