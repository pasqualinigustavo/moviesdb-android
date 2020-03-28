package com.moviesdb.ui.splash

import android.util.Log
import com.moviesdb.data.Cache
import com.moviesdb.ui.home.SplashInteractor
import com.moviesdb.ui.splash.router.SplashRouter

class SplashPresenter(val interactor: SplashInteractor, private val router: SplashRouter) {

    private var mView: SplashView? = null

    companion object {
        val TAG = SplashPresenter::class.java.simpleName
    }

    fun bindView(view: SplashView) {
        mView = view
    }

    fun unbindView() {
        mView = null
    }

    private fun launchTheApp() {
        Log.d(TAG, "launchTheApp")
        router.openHomeActivity()
    }

    fun loadGenres() {
        Log.d(TAG, "loadGenres")
        interactor.loadGenres()
                .subscribe({ response ->
                    Cache.cacheGenres(response.genres)
                    launchTheApp()
                }, {
                    launchTheApp()
                })
    }
}