package io.bechitra.currencyanalyzer.sharedpreference

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.bechitra.currencyanalyzer.network.Currency
import java.lang.reflect.Type


class PreConfig {
    private val LIST_KEY = "list_key"

    public fun writeListInPref(context:Context, currencydata: MutableList<Currency>?) {
        val gson = Gson()
        val jsonString = gson.toJson(currencydata)
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
       editor.putString(LIST_KEY,jsonString)
        editor.apply()
    }

    public fun readListFromPref(context: Context) : List<io.bechitra.currencyanalyzer.network.Currency>? {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString = pref.getString(LIST_KEY, "")
        val gson = Gson()
        val type: Type = object : TypeToken<MutableList<io.bechitra.currencyanalyzer.network.Currency>>() {}.type
        return gson.fromJson<MutableList<io.bechitra.currencyanalyzer.network.Currency>>(jsonString, type)
    }

}

