package com.revolut.rates.base

import android.arch.lifecycle.ViewModel
import com.revolut.rates.injection.component.DaggerViewModelInjector
import com.revolut.rates.injection.component.ViewModelInjector
import com.revolut.rates.injection.module.NetworkModule
import com.revolut.rates.ui.rate.RateListViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is RateListViewModel -> injector.inject(this)
        }
    }
}