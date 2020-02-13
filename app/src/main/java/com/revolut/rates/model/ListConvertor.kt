package com.revolut.rates.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


object ListConvertor {

    @JvmStatic
    fun storedStringToStrings(data: String?): List<String> {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<String>>() {

        }.type
        return gson.fromJson(data, listType)
    }

    @JvmStatic
    fun stringsToStoredString(myObjects: List<String>): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

}