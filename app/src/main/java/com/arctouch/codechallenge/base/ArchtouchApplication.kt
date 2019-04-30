package com.arctouch.codechallenge.base

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import android.util.Log

import com.arctouch.codechallenge.di.ApplicationDependency
import com.arctouch.codechallenge.di.components.ApplicationComponent
import com.arctouch.codechallenge.di.components.ComponentHolder

class ArchtouchApplication : MultiDexApplication() {

    private var instance: ArchtouchApplication? = null
    lateinit var graph: ApplicationComponent

    companion object {
        var TAG = ArchtouchApplication.javaClass.canonicalName
        lateinit var appContext: Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initializeInjector() {
        graph = ApplicationDependency().getApplicationComponent(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return graph
    }

    override fun onCreate() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate()
        instance = this

        this.initializeInjector()
        graph.inject(null)

        // Context
        appContext = applicationContext
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        when (level) {
            TRIM_MEMORY_COMPLETE -> this.tryToCleanMemory()

            TRIM_MEMORY_UI_HIDDEN -> this.tryToCleanMemory()

            TRIM_MEMORY_RUNNING_CRITICAL -> this.cleanMemoryCache()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(TAG, "onLowMemory")
        this.cleanMemoryCache()
    }

    private fun tryToCleanMemory() {
        this.cleanMemoryCache()
        System.gc()
    }

    private fun cleanMemoryCache() {

    }


}