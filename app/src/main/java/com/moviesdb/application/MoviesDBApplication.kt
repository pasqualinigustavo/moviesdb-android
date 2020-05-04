package com.moviesdb.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.moviesdb.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MoviesDBApplication : Application(), HasActivityInjector {

    private var instance: MoviesDBApplication? = null
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    companion object {
        var TAG = MoviesDBApplication.javaClass.canonicalName
        lateinit var appContext: Context
    }

    override fun onCreate() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate()
        instance = this

        // Context
        appContext = applicationContext
        instance = this

        AppInjector.init(this)
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

    override
    fun activityInjector() = dispatchingAndroidInjector
}