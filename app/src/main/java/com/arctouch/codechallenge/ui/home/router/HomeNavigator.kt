package com.arctouch.codechallenge.ui.home.router

import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseFragment
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.home.HomeActivity
import com.arctouch.codechallenge.ui.moviedetails.MovieDetailsFragment

class HomeNavigator(private val activity: HomeActivity) {

    companion object {
        private val TAG = HomeNavigator::class.java.simpleName
    }

    fun clearFragmentsStack() {
        activity.clearFragmentStack()
    }

    fun showMovieDetails(movie: Movie) {
        switchContent(MovieDetailsFragment.newInstance(movie), true)
    }

    fun switchContent(fragment: BaseFragment, addToBackStack: Boolean) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.canonicalName
        transaction.replace(R.id.activity_content, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
        activity.displayHomeUp()
    }

    fun clearBackStack() {
        val fm = activity.supportFragmentManager
        val count = fm.backStackEntryCount
        (0 until count).forEach { _ -> fm.popBackStack() }
    }
}