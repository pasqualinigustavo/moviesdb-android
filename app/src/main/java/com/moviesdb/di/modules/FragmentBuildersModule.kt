package com.moviesdb.di.modules

import com.moviesdb.ui.movies.UpcomingMoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeUpcomingMoviesFragment(): UpcomingMoviesFragment
}
