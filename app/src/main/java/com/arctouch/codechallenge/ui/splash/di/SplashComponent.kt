package com.archtouch.codechallenge.splash.di

import com.arctouch.codechallenge.di.PerActivity
import com.arctouch.codechallenge.di.components.ApplicationComponent
import com.arctouch.codechallenge.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(modules = arrayOf(SplashModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface SplashComponent {
    fun inject(target: SplashActivity)

    @Component.Builder
    interface Builder {
        fun parent(parent: ApplicationComponent): Builder
        fun module(module: SplashModule): Builder
        @BindsInstance
        fun target(activity: SplashActivity): Builder

        fun build(): SplashComponent
    }
}