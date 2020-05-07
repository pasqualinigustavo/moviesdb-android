package com.moviesdb.application

import android.content.Context
import android.os.Bundle

class AndroidAnalyticsProvider(context: Context) :
        AnalyticsProvider {

    override fun logEvent(event: String, params: Map<String, String>) {
        val bundle = Bundle()
        for (key in params.keys) {
            bundle.putString(key, params[key])
        }
    }
}

interface AnalyticsProvider {
    fun logEvent(event: String, params: Map<String, String>)
}