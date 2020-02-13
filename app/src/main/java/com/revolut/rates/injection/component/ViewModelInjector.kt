package com.revolut.rates.injection.component

import com.revolut.rates.injection.module.NetworkModule
import com.revolut.rates.ui.rate.RateListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(rateListViewModel: RateListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}