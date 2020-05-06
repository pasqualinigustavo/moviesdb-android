package com.moviesdb.ui.movies

import androidx.lifecycle.MutableLiveData
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.commom.extension.subscribeWithDefaultHandlers
import com.moviesdb.domain.GetUpcomingMoviesUsecase
import com.moviesdb.model.Movie
import com.moviesdb.navigator.Navigator
import com.moviesdb.rest.SchedulerProvider
import com.moviesdb.ui.BaseViewModel
import com.moviesdb.util.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Named

class UpcomingMoviesViewModel
@Inject constructor(
        analyticsProvider: AnalyticsProvider,
        schedulerProviderFacade: SchedulerProvider,
        @Named(Navigator.DASHBOARD) navigator: Navigator,
        private val getUpcomingMoviesUsecase: GetUpcomingMoviesUsecase
) : BaseViewModel(schedulerProviderFacade, analyticsProvider, navigator) {

    val itemsList = ArrayList<Movie>()
    val onItemsData = MutableLiveData<List<Movie>>()
    val emptyStateEvent = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<Boolean>()
    var currentPage = 1L

    override fun onAttached() {
        super.onAttached()
        getItemsFromServer()
    }

    fun bumpPage() {
        currentPage++
    }

    fun getItemsFromServer() {
        disposables.add(
                getUpcomingMoviesUsecase.getUpcomingMovies(currentPage)
                        .doOnSubscribe {
                            loading.postValue(true)
                        }
                        .subscribeWithDefaultHandlers(
                                this@UpcomingMoviesViewModel,
                                {
                                    getItemsFromServer()
                                },
                                { result ->
                                    loading.postValue(false)

                                    itemsList.addAll(result.results)

                                    if (itemsList.isEmpty())
                                        emptyStateEvent.postValue(true)
                                    else
                                        onItemsData.postValue(result.results)

                                }, {
                            //                            canRequestMoreElements()
                            loading.postValue(false)
                            showError.postValue(true)
                        }
                        )
        )
    }

    fun onMovieClicked(movie: Movie) {
        navigator.dispatchNavigationEvent(
                Navigator.NavigationEvent.MOVIE_DETAILS,
                movie
        )
    }
}