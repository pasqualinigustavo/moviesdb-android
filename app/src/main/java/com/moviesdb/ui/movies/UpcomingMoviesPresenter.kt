package com.moviesdb.ui.movies

import android.util.Log
import com.moviesdb.data.Cache
import com.moviesdb.model.Movie
import com.moviesdb.ui.movies.router.UpcomingMoviesRouter

/**
 * @author Gustavo Pasqualini
 */

class UpcomingMoviesPresenter constructor(
        private val interactor: UpcomingMoviesInteractor,
        private val router: UpcomingMoviesRouter
) {

    companion object {
        val TAG = UpcomingMoviesPresenter::class.java.simpleName
    }

    private var mView: UpcomingMoviesView? = null

    fun bindView(view: UpcomingMoviesView) {
        this.mView = view
        Log.d(TAG, "bindView")
    }

    fun unbindView() {
        this.mView = null
    }

    fun getUpcomingMovies(currentPage: Long) {
        Log.d(TAG, "getUpcomingMovies")
        interactor.getUpcomingMovies(currentPage)
                .doOnSubscribe { mView?.showLoadingView(true) }
                .subscribe({ response ->
                    mView?.showLoadingView(false)
                    val moviesWithGenres = response.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    mView?.showUpcomingMovies(moviesWithGenres)
                }, {
                    mView?.showLoadingView(false)
                    mView?.showUpcomingMovies(null)
                })
    }

    fun showMovieDetails(movie: Movie) {
        router.showMovieDetails(movie)
    }
}
