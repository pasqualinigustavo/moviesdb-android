package com.arctouch.codechallenge.activities.home.router

import androidx.navigation.Navigation
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.activities.home.HomeActivity
import com.arctouch.codechallenge.ui.movies.MoviesFragmentDirections

class HomeNavigator(private val activity: HomeActivity) {

    companion object {
        private val TAG = HomeNavigator::class.java.simpleName
    }

    fun showMovieDetails(movie: Movie) {
        val navController = Navigation.findNavController(activity, R.id.nav_host)

//      //with args bundle
//        val args = MovieDetailsFragment.bundleArgs(movie)
//        navController.navigate(R.id.movieDetailsFragment, args)

//      //safe args
        val dir = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movie)
        navController.navigate(dir)
    }
}