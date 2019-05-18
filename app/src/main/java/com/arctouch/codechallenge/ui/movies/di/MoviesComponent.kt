package com.arctouch.codechallenge.ui.movies.di

import com.arctouch.codechallenge.di.annotations.PerFragment
import com.arctouch.codechallenge.activities.home.di.HomeComponent
import com.arctouch.codechallenge.ui.movies.MoviesFragment
import dagger.Component

@PerFragment
@Component(
     dependencies = arrayOf(HomeComponent::class),
     modules = arrayOf(MoviesModule::class)
)
interface MoviesComponent {

    fun inject(target: MoviesFragment)
}