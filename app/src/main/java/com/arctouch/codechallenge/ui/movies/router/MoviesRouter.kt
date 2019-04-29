package com.arctouch.codechallenge.ui.movies.router

import com.arctouch.codechallenge.model.Movie

interface MoviesRouter {
    fun showMovieDetails(movie: Movie)
}