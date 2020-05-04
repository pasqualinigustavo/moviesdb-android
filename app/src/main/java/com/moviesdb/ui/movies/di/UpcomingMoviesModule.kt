package com.moviesdb.ui.movies.di

import com.moviesdb.di.annotations.PerFragment
import com.moviesdb.rest.APIComm
import com.moviesdb.ui.home.router.HomeNavigator
import com.moviesdb.ui.movies.UpcomingMoviesInteractor
import com.moviesdb.ui.movies.UpcomingMoviesPresenter
import com.moviesdb.ui.movies.router.UpcomingMoviesActivityRouter
import com.moviesdb.ui.movies.router.UpcomingMoviesRouter
import dagger.Module
import dagger.Provides

//@Module
//class UpcomingMoviesModule {
//
//    @Provides
//    @PerFragment
//    fun presenter(interactor: UpcomingMoviesInteractor, router: UpcomingMoviesRouter) =
//            UpcomingMoviesPresenter(interactor, router)
//
//    @Provides
//    @PerFragment
//    fun interactor(apiComm: APIComm) = UpcomingMoviesInteractor(apiComm)
//
//    @Provides
//    @PerFragment
//    fun router(navigator: HomeNavigator): UpcomingMoviesRouter =
//            UpcomingMoviesActivityRouter(navigator)
//}