package io.bechitra.currencyanalyzer.network

import com.google.gson.annotations.SerializedName

class CurrencyList(@SerializedName("pagination") var pagedata : pageData,
                   @SerializedName("data") var data : List<CurrencyData>
                   )
