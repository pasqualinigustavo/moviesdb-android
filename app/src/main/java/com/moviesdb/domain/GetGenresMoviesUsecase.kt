package com.moviesdb.domain

import com.moviesdb.app.UpcomingMoviesRepository
import com.moviesdb.model.GenreResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGenresMoviesUsecase @Inject constructor(
        var upcomingMoviesRepository: UpcomingMoviesRepository
) {

    fun getGenres(): Observable<GenreResponse> {
        return upcomingMoviesRepository.genres().map { it }
    }
}