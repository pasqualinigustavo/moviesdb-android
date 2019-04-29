package com.arctouch.codechallenge.ui.moviedetails

interface MovieDetailsView {
    fun showError(message: String)
    fun showProgressDialog()
    fun hideProgressDialog()
}