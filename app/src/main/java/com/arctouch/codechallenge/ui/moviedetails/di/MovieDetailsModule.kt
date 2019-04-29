package com.arctouch.codechallenge.ui.moviedetails.di

import com.arctouch.codechallenge.di.PerFragment
import com.arctouch.codechallenge.rest.APIComm
import com.arctouch.codechallenge.ui.moviedetails.MovieDetailsInteractor
import com.arctouch.codechallenge.ui.moviedetails.MovieDetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule {

    @Provides
    @PerFragment
    fun presenter(interactor: MovieDetailsInteractor) =
            MovieDetailsPresenter(interactor)

    @Provides
    @PerFragment
    fun interactor(apiComm: APIComm) = MovieDetailsInteractor(apiComm)

}