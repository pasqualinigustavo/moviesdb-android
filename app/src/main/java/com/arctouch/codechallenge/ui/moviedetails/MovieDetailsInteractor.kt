package com.arctouch.codechallenge.ui.moviedetails

import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.rest.APIComm
import com.arctouch.codechallenge.util.ArchtouchConstants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsInteractor(val apiComm: APIComm) {

    private companion object {
        val TAG: String = MovieDetailsInteractor::class.java.simpleName
    }

    fun getMovies(id: Long): Observable<Movie> {
        return apiComm.moviesEndPoint().movie(id, ArchtouchConstants.API_KEY, ArchtouchConstants.DEFAULT_LANGUAGE).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}