package com.moviesdb.ui.movies.di

import com.moviesdb.di.annotations.PerFragment
import com.moviesdb.ui.home.di.HomeComponent
import com.moviesdb.ui.movies.UpcomingMoviesFragment
import dagger.Component

@PerFragment
@Component(
        dependencies = arrayOf(HomeComponent::class),
        modules = arrayOf(UpcomingMoviesModule::class)
)
interface UpcomingMoviesComponent {

    fun inject(target: UpcomingMoviesFragment)
}