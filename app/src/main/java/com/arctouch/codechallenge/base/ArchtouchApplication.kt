package com.arctouch.codechallenge.base

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.arctouch.codechallenge.di.ApplicationDependency
import com.arctouch.codechallenge.di.components.AppComponent

class ArchtouchApplication : MultiDexApplication() {

    private var instance: ArchtouchApplication? = null
    lateinit var graph: AppComponent

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

    fun getApplicationComponent(): AppComponent {
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