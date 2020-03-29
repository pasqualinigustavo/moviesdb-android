package com.moviesdb.splash.di

import com.moviesdb.splash.router.SplashActivityRouter
import com.moviesdb.splash.router.SplashNavigator
import com.moviesdb.splash.router.SplashRouter
import com.moviesdb.di.annotations.PerActivity
import com.moviesdb.rest.APIComm
import com.moviesdb.ui.home.SplashInteractor
import com.moviesdb.ui.splash.SplashActivity
import com.moviesdb.ui.splash.SplashPresenter
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
    fun presenter(router: SplashRouter, interactor: SplashInteractor) = SplashPresenter(router, interactor)

    @Provides
    @PerActivity
    fun interactor(apiComm: APIComm) = SplashInteractor(apiComm)
}