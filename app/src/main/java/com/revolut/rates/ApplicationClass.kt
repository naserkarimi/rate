package com.revolut.rates

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class ApplicationClass : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}