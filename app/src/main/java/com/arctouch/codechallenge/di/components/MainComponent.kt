package com.arctouch.codechallenge.di.components

import com.arctouch.codechallenge.di.PerActivity
import com.arctouch.codechallenge.ui.home.HomeActivity
import com.arctouch.codechallenge.ui.home.di.HomeModule
import com.arctouch.codechallenge.ui.home.router.HomeNavigator
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(HomeModule::class))
interface MainComponent {

    fun inject(item: HomeActivity)
    fun provideMainNavigator(): HomeNavigator

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun target(activity: HomeActivity): Builder

        fun module(module: HomeModule): Builder
        fun parent(applicationComponent: ApplicationComponent): Builder
        fun build(): MainComponent
    }
}
