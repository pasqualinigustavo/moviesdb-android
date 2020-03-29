package com.moviesdb.ui.movies.details

import com.moviesdb.rest.APIComm

class MovieDetailsInteractor(private val apiComm: APIComm) {

    private companion object {
        val TAG: String = MovieDetailsInteractor::class.java.simpleName
    }
}