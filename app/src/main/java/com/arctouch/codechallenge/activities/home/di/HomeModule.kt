package com.arctouch.codechallenge.activities.home.di

import com.arctouch.codechallenge.di.annotations.PerActivity
import com.arctouch.codechallenge.activities.home.HomeActivity
import com.arctouch.codechallenge.activities.home.HomePresenter
import com.arctouch.codechallenge.activities.home.router.HomeActivityRouter
import com.arctouch.codechallenge.activities.home.router.HomeNavigator
import com.arctouch.codechallenge.activities.home.router.HomeRouter
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