package com.moviesdb.ui.home.di

import com.moviesdb.di.annotations.PerActivity
import com.moviesdb.rest.APIComm
import com.moviesdb.ui.home.HomeActivity
import com.moviesdb.ui.home.HomeInteractor
import com.moviesdb.ui.home.HomePresenter
import com.moviesdb.ui.home.router.HomeActivityRouter
import com.moviesdb.ui.home.router.HomeNavigator
import com.moviesdb.ui.home.router.HomeRouter
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    @PerActivity
    fun router(navigator: HomeNavigator): HomeRouter = HomeActivityRouter(navigator)

    @Provides
    @PerActivity
    fun navigator(activity: HomeActivity) = HomeNavigator(activity)

    @Provides
    @PerActivity
    fun interactor(apiComm: APIComm) = HomeInteractor(apiComm)

    @Provides
    @PerActivity
    fun presenter(router: HomeRouter, interactor: HomeInteractor) =
            HomePresenter(router, interactor)
}