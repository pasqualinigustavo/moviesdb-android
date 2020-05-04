package com.moviesdb.ui.movies.details.di

import com.moviesdb.di.annotations.PerFragment
import com.moviesdb.rest.APIComm
import com.moviesdb.ui.movies.details.MovieDetailsInteractor
import com.moviesdb.ui.movies.details.MovieDetailsPresenter
import dagger.Module
import dagger.Provides

//@Module
//class MovieDetailsModule {
//
//    @Provides
//    @PerFragment
//    fun presenter(interactor: MovieDetailsInteractor) =
//            MovieDetailsPresenter(interactor)
//
//    @Provides
//    @PerFragment
//    fun interactor(apiComm: APIComm) = MovieDetailsInteractor(apiComm)
//
//}