package io.bechitra.currencyanalyzer.network

import com.google.gson.annotations.SerializedName

data class CurrencyData(
    val data: List<Currency> = listOf()

)

data class Currency(
        @SerializedName("date") var date : String,
        @SerializedName("source") var source:String,
        @SerializedName("rate") var rate: Double,
        @SerializedName("destination") var destination:String,
      

//        val close:Double,
//        val date:String,
//        val from:String,
//        val high:Double,
//        val low:Double,
//        val open:Double,
//        val rate:Double,
//        val to : String
)