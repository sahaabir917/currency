package io.bechitra.currencyanalyzer.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.bechitra.currencyanalyzer.room.entity.Currency

@Dao
interface CurrencyDao {
    @Query("select * from currency_data")
    fun getCurrencyData() : LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currency: Currency)

    @Update
    suspend fun update(currency: Currency)

    @Delete
    suspend fun delete(currency: Currency)

    @Query("delete from currency_data")
    suspend fun reset()
}