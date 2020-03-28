package com.moviesdb.ui.splash.router

class SplashActivityRouter(private val navigator: SplashNavigator) : SplashRouter {

    override fun openHomeActivity() {
        navigator.openHomeActivity()
    }
}