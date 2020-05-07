package com.moviesdb.app

import com.moviesdb.model.GenreResponse
import com.moviesdb.model.UpcomingMoviesResponse
import com.moviesdb.rest.APIComm
import com.moviesdb.rest.endpoint.TmdbApi
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class UpcomingMoviesRepository @Inject constructor(var apiComm: APIComm) {

    fun getUpcomingMovies(page: Long): Observable<UpcomingMoviesResponse> {
        return apiComm.tmdbEndpoint().upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
    }

    fun genres(): Observable<GenreResponse> {
        return apiComm.tmdbEndpoint().genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
    }
}