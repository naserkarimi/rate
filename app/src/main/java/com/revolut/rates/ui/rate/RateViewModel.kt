package com.revolut.rates.ui.rate

import android.arch.lifecycle.MutableLiveData
import com.revolut.rates.base.BaseViewModel
import com.revolut.rates.utils.getCountryName


class RateViewModel : BaseViewModel() {
    private val rateTitle = MutableLiveData<String>()
    private val rateDes = MutableLiveData<String>()
    private val rateImagePath = MutableLiveData<String>()
    private val rateAmount = MutableLiveData<String>()
    private var imagesUrl = "https://valuta.exchange/img/flags/usd-flag.jpg"

    fun bind(rate: Pair<String, String>) {
        rateTitle.value = rate.first
        rateDes.value = rate.first
        rateImagePath.value = imagesUrl.replace("usd", rate.first.toLowerCase())
        rateAmount.value = rate.second
    }

    fun getRateTitle(): MutableLiveData<String> {
        return rateTitle
    }

    fun getRateDes(): MutableLiveData<String> {
        return getCountryName(rateDes)
    }

    fun getRateImagePath(): MutableLiveData<String> {
        return rateImagePath
    }

    fun getRateAmount(): MutableLiveData<String> {
         return rateAmount
    }
}
