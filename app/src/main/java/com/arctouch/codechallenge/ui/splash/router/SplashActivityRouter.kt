package com.archtouch.codechallenge.splash.router

class SplashActivityRouter(private val navigator: SplashNavigator) : SplashRouter {

    override fun openMainActivity() {
        navigator.openMainActivity()
    }

}