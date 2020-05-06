package com.moviesdb.ui.movies.details

import androidx.lifecycle.MutableLiveData
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.model.Movie
import com.moviesdb.navigator.Navigator
import com.moviesdb.rest.SchedulerProvider
import com.moviesdb.ui.BaseViewModel
import javax.inject.Inject
import javax.inject.Named

class MovieDetailsViewModel
@Inject constructor(
        schedulerProviderFacade: SchedulerProvider,
        @Named(Navigator.DASHBOARD) navigator: Navigator,
        analyticsProvider: AnalyticsProvider
) :
        BaseViewModel(schedulerProviderFacade, analyticsProvider, navigator) {

    val basketPayables = MutableLiveData<Movie>()

    override fun onAttached() {
        super.onAttached()
        val movie = params.value as Movie
        showMovie(movie)
    }

    private fun showMovie(movie: Movie) {
        basketPayables.postValue(movie)
    }

}
