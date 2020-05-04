package com.moviesdb.ui.home.di

import com.moviesdb.di.annotations.PerActivity
import com.moviesdb.di.components.AppComponent
import com.moviesdb.ui.home.HomeActivity
import com.moviesdb.ui.home.router.HomeNavigator
import dagger.BindsInstance
import dagger.Component

//@PerActivity
//@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(HomeModule::class))
//interface HomeComponent {
//
//    fun inject(item: HomeActivity)
//    fun provideMainNavigator(): HomeNavigator
//
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun target(activity: HomeActivity): Builder
//
//        fun module(module: HomeModule): Builder
//        fun parent(appComponent: AppComponent): Builder
//        fun build(): HomeComponent
//    }
//}
