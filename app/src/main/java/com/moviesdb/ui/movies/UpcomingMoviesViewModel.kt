package com.moviesdb.ui.movies

import androidx.lifecycle.MutableLiveData
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.commom.extension.subscribeWithDefaultHandlers
import com.moviesdb.domain.GetUpcomingMoviesUsecase
import com.moviesdb.model.Movie
import com.moviesdb.rest.SchedulerProvider
import com.moviesdb.ui.BaseViewModel
import javax.inject.Inject

class UpcomingMoviesViewModel
@Inject constructor(
        analyticsProvider: AnalyticsProvider,
        schedulerProviderFacade: SchedulerProvider,
        private val getUpcomingMoviesUsecase: GetUpcomingMoviesUsecase
) : BaseViewModel(schedulerProviderFacade, analyticsProvider) {

    val messagesList = ArrayList<Movie>()
    val onMessagesData = MutableLiveData<List<Movie>>()
    val emptyStateEvent = MutableLiveData<Boolean>()
//    val showError = SingleLiveEvent<Boolean>()

    override fun onAttached() {
        super.onAttached()
        getPageDataFromServer()
    }

    fun getPageDataFromServer() {
        disposables.add(
                getUpcomingMoviesUsecase.getUpcomingMovies(1)
                        .doOnSubscribe {
                            //                            loading.postValue(true)
                        }
                        .subscribeWithDefaultHandlers(
                                this@UpcomingMoviesViewModel,
                                {
                                    getPageDataFromServer()
                                },
                                { result ->
                                    loading.postValue(false)

                                    messagesList.addAll(result.results)
//                                    hasMoreElements = messagesList.size < result.totalResults

//                                    canRequestMoreElements()

                                    if (messagesList.isEmpty())
                                        emptyStateEvent.postValue(true)
                                    else
                                        onMessagesData.postValue(result.results)

                                }, {
                            //                            canRequestMoreElements()
//                            loading.postValue(false)
//                            showError.postValue(true)
                        }
                        )
        )
    }
}