package com.moviesdb.ui.movies

import com.moviesdb.model.UpcomingMoviesResponse
import com.moviesdb.rest.APIComm
import com.moviesdb.rest.endpoint.TmdbApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpcomingMoviesInteractor(val apiComm: APIComm) {

    private companion object {
        val TAG: String = UpcomingMoviesInteractor::class.java.simpleName
    }

    fun getUpcomingMovies(page: Long): Observable<UpcomingMoviesResponse> {
        return apiComm.tmdbEndpoint().upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}