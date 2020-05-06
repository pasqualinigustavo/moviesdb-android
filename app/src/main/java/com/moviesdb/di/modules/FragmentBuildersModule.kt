package com.moviesdb.di.modules

import com.moviesdb.ui.movies.UpcomingMoviesFragment
import com.moviesdb.ui.movies.details.MovieDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeUpcomingMoviesFragment(): UpcomingMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment
}
