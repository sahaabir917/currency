package io.bechitra.currencyanalyzer.network

import com.google.gson.annotations.SerializedName

data class pageData (
        @SerializedName("currentPage") var currentPage : String,
        @SerializedName("hasNext") var hasNext :String,
        @SerializedName("hasPrev") var hasPrev:String,
        @SerializedName("next") var next : String,
        @SerializedName("pages") var pages : Int,
        @SerializedName("prev") var prev : String,
        @SerializedName("size") var size : Int,
        @SerializedName("totalElements") var totalElements : Int
)