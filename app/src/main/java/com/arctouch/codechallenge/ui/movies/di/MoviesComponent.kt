package com.arctouch.codechallenge.ui.movies.di

import com.arctouch.codechallenge.di.PerFragment
import com.arctouch.codechallenge.di.components.MainComponent
import com.arctouch.codechallenge.ui.movies.MoviesFragment
import dagger.Component

@PerFragment
@Component(
        dependencies = arrayOf(MainComponent::class),
        modules = arrayOf(MoviesModule::class)
)
interface MoviesComponent {

    fun inject(target: MoviesFragment)
}