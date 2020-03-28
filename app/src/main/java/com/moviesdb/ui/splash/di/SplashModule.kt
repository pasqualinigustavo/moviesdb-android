package com.moviesdb.ui.splash.di

import com.moviesdb.di.annotations.PerActivity
import com.moviesdb.rest.APIComm
import com.moviesdb.ui.home.SplashInteractor
import com.moviesdb.ui.splash.SplashActivity
import com.moviesdb.ui.splash.SplashPresenter
import com.moviesdb.ui.splash.router.SplashActivityRouter
import com.moviesdb.ui.splash.router.SplashNavigator
import com.moviesdb.ui.splash.router.SplashRouter
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
    fun presenter(interactor: SplashInteractor, router: SplashRouter) = SplashPresenter(interactor, router)

    @Provides
    @PerActivity
    fun interactor(apiComm: APIComm) = SplashInteractor(apiComm)
}