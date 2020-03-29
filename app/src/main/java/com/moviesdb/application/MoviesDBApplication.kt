package com.moviesdb.application

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.moviesdb.di.ApplicationDependency
import com.moviesdb.di.components.AppComponent
import com.moviesdb.model.interfaces.LifecycleDelegate

class MoviesDBApplication : MultiDexApplication(), LifecycleDelegate {

    private var instance: MoviesDBApplication? = null
    lateinit var graph: AppComponent

    companion object {
        var TAG = MoviesDBApplication.javaClass.canonicalName
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

    override fun onAppBackgrounded() {
        Log.d(TAG, "App in background")
    }

    override fun onAppForegrounded() {
        Log.d(TAG, "App in foreground")
    }

    override fun onCreate() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate()
        instance = this

        this.initializeInjector()
        graph.inject(null)

        // Context
        appContext = applicationContext
        instance = this

        val lifeCycleHandler = AppLifecycleHandler(this)
        registerLifecycleHandler(lifeCycleHandler)
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

    private fun registerLifecycleHandler(lifeCycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }

    internal inner class AppLifecycleHandler(private val lifecycleDelegate: LifecycleDelegate) : Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

        private var appInForeground = false

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {
            if (!appInForeground) {
                appInForeground = true
                lifecycleDelegate.onAppForegrounded()
            }
        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {

        }

        override fun onTrimMemory(level: Int) {
            if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
                appInForeground = false
                lifecycleDelegate.onAppBackgrounded()
            }
        }

        override fun onConfigurationChanged(newConfig: Configuration) {

        }

        override fun onLowMemory() {

        }
    }
}