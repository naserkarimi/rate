package com.revolut.rates.utils

import android.arch.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.HashMap


var countries = HashMap<String, String>()

fun getCountryName(shortName: MutableLiveData<String>): MutableLiveData<String> {
    if (countries.size == 0) {
        for (iso in Locale.getISOCountries()) {
            val l = Locale("", iso)
            if (Currency.getInstance(l) != null)
                countries[Currency.getInstance(l).currencyCode.toUpperCase()] = l.displayCountry
        }
    }
    val name: MutableLiveData<String> = MutableLiveData()
    name.value = countries[shortName.value]
    return name
}

