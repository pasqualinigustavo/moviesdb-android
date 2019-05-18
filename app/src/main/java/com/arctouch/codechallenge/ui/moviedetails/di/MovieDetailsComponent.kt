package com.arctouch.codechallenge.ui.moviedetails.di

import com.arctouch.codechallenge.di.annotations.PerFragment
import com.arctouch.codechallenge.activities.home.di.HomeComponent
import com.arctouch.codechallenge.ui.moviedetails.MovieDetailsFragment
import dagger.Component

@PerFragment
@Component(
        dependencies = arrayOf(HomeComponent::class),
        modules = arrayOf(MovieDetailsModule::class)
)
interface MovieDetailsComponent {

    fun inject(target: MovieDetailsFragment)
}