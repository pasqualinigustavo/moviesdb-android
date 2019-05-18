package com.arctouch.codechallenge.ui.movies.di

import com.arctouch.codechallenge.activities.home.router.HomeNavigator
import com.arctouch.codechallenge.di.annotations.PerFragment
import com.arctouch.codechallenge.rest.APIComm
import com.arctouch.codechallenge.ui.movies.MoviesInteractor
import com.arctouch.codechallenge.ui.movies.MoviesPresenter
import com.arctouch.codechallenge.ui.movies.router.MoviesActivityRouter
import com.arctouch.codechallenge.ui.movies.router.MoviesRouter
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @Provides
    @PerFragment
    fun presenter(interactor: MoviesInteractor, router: MoviesRouter) =
            MoviesPresenter(interactor, router)

    @Provides
    @PerFragment
    fun interactor(apiComm: APIComm) = MoviesInteractor(apiComm)

    @Provides
    @PerFragment
    fun router(navigator: HomeNavigator): MoviesRouter =
            MoviesActivityRouter(navigator)
}