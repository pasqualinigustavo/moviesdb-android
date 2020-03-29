package com.moviesdb.ui.home.router

import androidx.navigation.Navigation
import com.moviesdb.R
import com.moviesdb.model.Movie
import com.moviesdb.ui.home.HomeActivity
import com.moviesdb.ui.movies.details.MovieDetailsFragment

class HomeNavigator(private val activity: HomeActivity) {

    companion object {
        private val TAG = HomeNavigator::class.java.simpleName
    }

    fun showMovieDetails(movie: Movie) {
        val navController = Navigation.findNavController(activity, R.id.nav_host)

        //by bundle
        val args = MovieDetailsFragment.bundleArgs(movie)
        navController.navigate(R.id.movieDetailsFragment, args)
    }
}