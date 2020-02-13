package com.revolut.rates.network

import com.revolut.rates.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RateApi {
    @GET("latest")
    fun getRates(@Query("base") action: String): Observable<Response>
}