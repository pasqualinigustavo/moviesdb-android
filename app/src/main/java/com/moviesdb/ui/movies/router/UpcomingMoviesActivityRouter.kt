package com.moviesdb.ui.movies.router

import com.moviesdb.model.Movie
import com.moviesdb.ui.home.router.HomeNavigator

class UpcomingMoviesActivityRouter(private val navigator: HomeNavigator) : UpcomingMoviesRouter {

    override fun showMovieDetails(movie: Movie) = navigator.showMovieDetails(movie)
}