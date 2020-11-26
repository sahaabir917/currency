package io.bechitra.currencyanalyzer.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class currencysettings(
   @PrimaryKey(autoGenerate = true)
   var id: Int = 0,


   @ColumnInfo(name = "country")
   var country: String = "",

   @ColumnInfo(name = "destinationcountry")
   var destinationcountry : String? = "",


   @ColumnInfo(name = "highrate")
   var highrate: Double? = null,

   @ColumnInfo(name = "lowrate")
    var lowrate : Double? = null,

   @ColumnInfo(name = "type")
   var type : String? = ""

)