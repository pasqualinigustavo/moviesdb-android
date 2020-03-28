package com.moviesdb.ui.movies

import com.moviesdb.model.Movie

interface UpcomingMoviesView {
    fun showError(message: String?)
    fun showUpcomingMovies(movies: List<Movie>?)
    fun showLoadingView(show: Boolean)
}