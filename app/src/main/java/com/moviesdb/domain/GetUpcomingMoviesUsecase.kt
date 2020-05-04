package com.moviesdb.domain

import com.moviesdb.app.UpcomingMoviesRepository
import com.moviesdb.model.UpcomingMoviesResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUpcomingMoviesUsecase @Inject constructor(
        var upcomingMoviesRepository: UpcomingMoviesRepository
) {
    fun getUpcomingMovies(page: Long): Observable<UpcomingMoviesResponse> {
        return upcomingMoviesRepository.getUpcomingMovies(page).map { it }
    }
}