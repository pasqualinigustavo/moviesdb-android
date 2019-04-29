package com.arctouch.codechallenge.ui.movies

import android.util.Log
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.ArchtouchApplication
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.movies.router.MoviesRouter

/**
 * @author Gustavo Pasqualini
 */

class MoviesPresenter constructor(private val interactor: MoviesInteractor, private val router: MoviesRouter) {

    companion object {
        val TAG = MoviesPresenter::class.java.simpleName
    }

    private var mView: MoviesView? = null

    fun attachView(view: MoviesView) {
        this.mView = view
        Log.d(TAG, "attachView")
    }

    fun detachView() {
        this.mView = null
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, error.message)
    }

    fun getMovies(currentPage: Int) {
        Log.d(TAG, "getMovies")
        interactor.getMovies(currentPage)
                .doOnSubscribe { mView?.showLoadingView(true) }
                .subscribe({ response ->
                    mView?.showLoadingView(false)
                    if (response != null) {
                        mView?.showMovies(response.results)
                    }
                }, { error ->
                    mView?.showLoadingView(false)
                    mView?.showError(ArchtouchApplication.getAppContext().getString(R.string.error_communication))
                })
    }

    fun showMovieDetails(movie: Movie) {
        router.showMovieDetails(movie)
    }
}
