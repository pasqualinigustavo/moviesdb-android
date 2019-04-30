package com.arctouch.codechallenge.ui.moviedetails

import android.util.Log
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.ArchtouchApplication

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

    fun getMovie(id: Long) {
        Log.d(TAG, "getMovie")
        interactor.getMovies(id)
                .doOnSubscribe { mView?.showProgressDialog() }
                .subscribe({ response ->
                    mView?.hideProgressDialog()
                    if (response != null) {
                        mView?.showMovie(response)
                    }
                }, { error ->
                    mView?.hideProgressDialog()
                    mView?.showError(ArchtouchApplication.appContext.getString(R.string.error_communication))
                })
    }
}
