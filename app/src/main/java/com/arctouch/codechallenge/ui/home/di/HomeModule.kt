package com.arctouch.codechallenge.ui.home.di

import com.arctouch.codechallenge.di.PerActivity
import com.arctouch.codechallenge.ui.home.HomeActivity
import com.arctouch.codechallenge.ui.home.HomePresenter
import com.arctouch.codechallenge.ui.home.router.HomeActivityRouter
import com.arctouch.codechallenge.ui.home.router.HomeNavigator
import com.arctouch.codechallenge.ui.home.router.HomeRouter
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
    fun presenter(router: HomeRouter) =
            HomePresenter(router)
}