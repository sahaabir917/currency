package io.bechitra.currencyanalyzer.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_data")
class Currency (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")val id : Long,
                @ColumnInfo(name = "date") val name: String,
                @ColumnInfo(name = "day") val day : Int,
                @ColumnInfo(name = "month") val month : Int,
                @ColumnInfo(name = "year") val year : Int,
                @ColumnInfo(name = "week") val week : Int,
                @ColumnInfo(name = "quartile") val quartile : Int,
                @ColumnInfo(name = "rate") val rate : Double
)