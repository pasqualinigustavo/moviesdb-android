package com.moviesdb.ui.home.router

import com.moviesdb.R
import com.moviesdb.navigator.NavigationController
import com.moviesdb.navigator.Navigator
import com.moviesdb.navigator.SharedEvents
import timber.log.Timber

class HomeNavigator(sharedEvents: SharedEvents) : Navigator(sharedEvents) {

    override fun handleNavigationEvent(
            navigationState: NavigationEventData,
            navController: NavigationController
    ): Boolean {
        Timber.d("MainNavigator navigation destination: " + navigationState.event.name)
        when (navigationState.event) {
            NavigationEvent.MOVIE_DETAILS -> navigate(
                    R.id.movieDetailsFragment,
                    navController,
                    navigationState.data
            )
            NavigationEvent.POP_BACKSTACK -> {
                if (!finish(navController)) {
                    return false
                }
            }
            else -> {
                Timber.d("MainNavigator unknow navigation destination: " + navigationState.event.name)
                return false
            }
        }
        return true
    }
}