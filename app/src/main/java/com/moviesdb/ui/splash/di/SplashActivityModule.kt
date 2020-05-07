package com.moviesdb.ui.splash.di

import com.moviesdb.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class SplashActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity
}