package com.moviesdb.ui.movies.router

import com.moviesdb.model.Movie

interface UpcomingMoviesRouter {
    fun showMovieDetails(movie: Movie)
}