package com.moviesdb.ui.home

import com.moviesdb.model.GenreResponse
import com.moviesdb.model.UpcomingMoviesResponse
import com.moviesdb.rest.APIComm
import com.moviesdb.rest.endpoint.TmdbApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashInteractor(private val apiComm: APIComm) {

    companion object {
        val TAG = SplashInteractor::class.java.simpleName
    }

    fun loadGenres(): Observable<GenreResponse> {
        return apiComm.tmdbEndpoint().genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}