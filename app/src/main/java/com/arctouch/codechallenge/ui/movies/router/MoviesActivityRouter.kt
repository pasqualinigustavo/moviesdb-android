package com.arctouch.codechallenge.ui.movies.router

import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.home.router.HomeNavigator

class MoviesActivityRouter(private val navigator: HomeNavigator) : MoviesRouter {

    override fun showMovieDetails(movie: Movie) = navigator.showMovieDetails(movie)
}