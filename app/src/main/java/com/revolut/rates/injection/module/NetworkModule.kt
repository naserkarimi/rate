package com.revolut.rates.injection.module

import com.revolut.rates.network.RateApi
import com.revolut.rates.utils.BASE_URL
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@Suppress("unused")
object NetworkModule {

    // for logging
//    val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//        this.level = HttpLoggingInterceptor.Level.BODY
//    }

    val client : OkHttpClient = OkHttpClient.Builder().apply {
//        this.addInterceptor(interceptor)
    }.build()

    @Provides
    @JvmStatic
    internal fun provideRateApi(retrofit: Retrofit): RateApi {
        return retrofit.create(RateApi::class.java)
    }

    @Provides
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build()
    }
}