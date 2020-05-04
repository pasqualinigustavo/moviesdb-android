package com.moviesdb.ui.home.di

import com.moviesdb.di.modules.FragmentBuildersModule
import com.moviesdb.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): HomeActivity
}