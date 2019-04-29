package com.archtouch.codechallenge.splash.di

import com.archtouch.codechallenge.splash.router.SplashActivityRouter
import com.archtouch.codechallenge.splash.router.SplashNavigator
import com.archtouch.codechallenge.splash.router.SplashRouter
import com.arctouch.codechallenge.di.PerActivity
import com.arctouch.codechallenge.ui.splash.SplashActivity
import com.arctouch.codechallenge.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    @PerActivity
    fun navigator(activity: SplashActivity) = SplashNavigator(activity)

    @Provides
    @PerActivity
    fun router(navigator: SplashNavigator): SplashRouter = SplashActivityRouter(navigator)

    @Provides
    @PerActivity
    fun presenter(router: SplashRouter) = SplashPresenter(router)
}