package com.arctouch.codechallenge.ui.moviedetails

import com.arctouch.codechallenge.model.Movie

interface MovieDetailsView {
    fun showError(message: String)
    fun showProgressDialog()
    fun showMovie(movie: Movie)
    fun hideProgressDialog()
}