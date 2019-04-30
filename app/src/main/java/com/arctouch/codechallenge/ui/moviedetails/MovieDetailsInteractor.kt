package com.arctouch.codechallenge.ui.moviedetails

import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.rest.APIComm
import com.arctouch.codechallenge.rest.RetrofitInit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsInteractor(private val apiComm: APIComm) {

    private companion object {
        val TAG: String = MovieDetailsInteractor::class.java.simpleName
    }

    fun getMovies(id: Long): Observable<Movie> {
        return apiComm.moviesEndPoint().movie(id, RetrofitInit.API_KEY, RetrofitInit.DEFAULT_LANGUAGE).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}