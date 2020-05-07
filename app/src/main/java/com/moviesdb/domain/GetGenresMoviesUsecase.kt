package com.moviesdb.domain

import com.moviesdb.app.UpcomingMoviesRepository
import com.moviesdb.model.GenreResponse
import com.moviesdb.model.UpcomingMoviesResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGenresMoviesUsecase @Inject constructor(
        var upcomingMoviesRepository: UpcomingMoviesRepository
) {
    fun getUpcomingMovies(page: Long): Observable<UpcomingMoviesResponse> {
        return upcomingMoviesRepository.getUpcomingMovies(page).map { it }
    }

    fun getGenres(): Observable<GenreResponse> {
        return upcomingMoviesRepository.genres().map { it }
    }
}