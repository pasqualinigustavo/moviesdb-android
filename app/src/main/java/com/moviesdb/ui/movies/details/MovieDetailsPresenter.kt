package com.moviesdb.ui.movies.details

import android.util.Log

/**
 * @author Gustavo Pasqualini
 */

class MovieDetailsPresenter constructor(
        private val interactor: MovieDetailsInteractor
) {

    companion object {
        val TAG = MovieDetailsPresenter::class.java.simpleName
    }

    private var mView: MovieDetailsView? = null

    fun bindView(view: MovieDetailsView) {
        this.mView = view
        Log.d(TAG, "bindView")
    }

    fun unbindView() {
        this.mView = null
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, error.message)
    }
}