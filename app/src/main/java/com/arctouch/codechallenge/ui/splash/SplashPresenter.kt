package com.arctouch.codechallenge.ui.splash

import android.util.Log
import com.archtouch.codechallenge.splash.SplashView
import com.archtouch.codechallenge.splash.router.SplashRouter

class SplashPresenter(private val router: SplashRouter) {

    private var mView: SplashView? = null

    companion object {
        val TAG = SplashPresenter::class.java.simpleName
    }

    var view: SplashView?
        get() = mView
        set(view) {
            this.mView = view
        }

    fun attachView(view: SplashView) {
        mView = view
    }

    fun detachView() {
        mView = null
    }

    fun init() {
        Log.d(TAG, "init")
        launchTheApp()
    }

    private fun launchTheApp() {
        Log.d(TAG, "launchTheApp")
        router.openMainActivity()
    }
}