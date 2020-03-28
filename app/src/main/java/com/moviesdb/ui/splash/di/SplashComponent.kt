package com.moviesdb.ui.splash.di

import com.moviesdb.di.annotations.PerActivity
import com.moviesdb.di.components.AppComponent
import com.moviesdb.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(modules = arrayOf(SplashModule::class), dependencies = arrayOf(AppComponent::class))
interface SplashComponent {
    fun inject(target: SplashActivity)

    @Component.Builder
    interface Builder {
        fun parent(parent: AppComponent): Builder
        fun module(module: SplashModule): Builder
        @BindsInstance
        fun target(activity: SplashActivity): Builder

        fun build(): SplashComponent
    }
}