package com.arctouch.codechallenge.ui.movies

import com.arctouch.codechallenge.model.Movie

interface MoviesView {
    fun showError(message: String)
    fun showMovies(movies: List<Movie>?)
    fun showLoadingView(show: Boolean)
}