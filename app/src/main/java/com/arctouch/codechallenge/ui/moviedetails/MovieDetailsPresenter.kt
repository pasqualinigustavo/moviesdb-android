package com.arctouch.codechallenge.ui.moviedetails

import android.util.Log

/**
 * @author Gustavo Pasqualini
 */

class MovieDetailsPresenter constructor(private val interactor: MovieDetailsInteractor) {

    companion object {
        val TAG = MovieDetailsPresenter::class.java.simpleName
    }

    private var mView: MovieDetailsView? = null

    fun attachView(view: MovieDetailsView) {
        this.mView = view
        Log.d(TAG, "attachView")
    }

    fun detachView() {
        this.mView = null
    }

    private fun onError(error: Throwable) {
        Log.e(TAG, error.message)
    }

    fun getMovieDetails() {
        Log.d(TAG, "getMovieDetails")
//        interactor.getMovies(currentPage)
//                .doOnSubscribe { mView?.showLoadingView(true) }
//                .subscribe({ response ->
//                    mView?.showLoadingView(false)
//                    if (response != null) {
//                        mView?.showMovies(response.results)
//                    }
//                }, { error ->
//                    mView?.showLoadingView(false)
//                    mView?.showError(ArchtouchApplication.getAppContext().getString(R.string.error_communication))
//                })
    }
}
