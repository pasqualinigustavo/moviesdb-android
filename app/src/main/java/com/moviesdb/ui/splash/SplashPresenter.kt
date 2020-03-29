package com.moviesdb.ui.splash

import android.util.Log
import com.moviesdb.data.Cache
import com.moviesdb.splash.SplashView
import com.moviesdb.splash.router.SplashRouter
import com.moviesdb.ui.home.SplashInteractor

class SplashPresenter(private val router: SplashRouter, val interactor: SplashInteractor) {

    private var mView: SplashView? = null

    companion object {
        val TAG = SplashPresenter::class.java.simpleName
    }

    var view: SplashView?
        get() = mView
        set(view) {
            this.mView = view
        }

    fun bindView(view: SplashView) {
        mView = view
    }

    fun unbindView() {
        mView = null
    }

    private fun launchTheApp() {
        Log.d(TAG, "launchTheApp")
        router.openMainActivity()
    }

    fun loadGenres() {
        Log.d(TAG, "loadGenres")
        interactor.loadGenres()
                .subscribe({ response ->
                    Cache.genres = response.genres
                    launchTheApp()
                }, {
                    launchTheApp()
                })
    }
}