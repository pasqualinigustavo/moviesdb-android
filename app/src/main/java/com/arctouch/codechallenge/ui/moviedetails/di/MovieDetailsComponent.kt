package com.arctouch.codechallenge.ui.moviedetails.di

import com.arctouch.codechallenge.di.PerFragment
import com.arctouch.codechallenge.di.components.MainComponent
import com.arctouch.codechallenge.ui.moviedetails.MovieDetailsFragment
import dagger.Component

@PerFragment
@Component(
        dependencies = arrayOf(MainComponent::class),
        modules = arrayOf(MovieDetailsModule::class)
)
interface MovieDetailsComponent {

    fun inject(target: MovieDetailsFragment)
}