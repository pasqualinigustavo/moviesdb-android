package com.arctouch.codechallenge.ui.movies

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.rest.APIComm
import com.arctouch.codechallenge.util.ArchtouchConstants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesInteractor(val apiComm: APIComm) {

    private companion object {
        val TAG: String = MoviesInteractor::class.java.simpleName
    }

    fun getMovies(currentPage: Int): Observable<UpcomingMoviesResponse> {
        return apiComm.moviesEndPoint().upcomingMovies(ArchtouchConstants.API_KEY, ArchtouchConstants.DEFAULT_LANGUAGE, currentPage, ArchtouchConstants.DEFAULT_REGION).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    fun getGenres(): Observable<GenreResponse> {
        return apiComm.moviesEndPoint().genres(ArchtouchConstants.API_KEY, ArchtouchConstants.DEFAULT_LANGUAGE).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}