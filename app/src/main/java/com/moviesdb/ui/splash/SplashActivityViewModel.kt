package com.moviesdb.ui.splash

import androidx.lifecycle.MutableLiveData
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.commom.extension.subscribeWithDefaultHandlers
import com.moviesdb.domain.GetGenresMoviesUsecase
import com.moviesdb.model.Genre
import com.moviesdb.navigator.Navigator
import com.moviesdb.rest.SchedulerProvider
import com.moviesdb.ui.BaseViewModel
import com.moviesdb.util.SingleLiveEvent
import javax.inject.Inject
import javax.inject.Named

class SplashActivityViewModel
@Inject constructor(
        analyticsProvider: AnalyticsProvider,
        schedulerProviderFacade: SchedulerProvider,
        @Named(Navigator.DASHBOARD) navigator: Navigator,
        private val getGenresMoviesUsecase: GetGenresMoviesUsecase
) : BaseViewModel(schedulerProviderFacade, analyticsProvider, navigator) {

    val onItemsData = MutableLiveData<List<Genre>>()
    val emptyStateEvent = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<Boolean>()

    override fun onAttached() {
        super.onAttached()
        getItemsFromServer()
    }

    fun getItemsFromServer() {
        disposables.add(
                getGenresMoviesUsecase.getGenres()
                        .doOnSubscribe {
                            loading.postValue(true)
                        }
                        .subscribeWithDefaultHandlers(
                                this@SplashActivityViewModel,
                                {
                                    getItemsFromServer()
                                },
                                { result ->
                                    loading.postValue(false)
                                    if (result.genres.isEmpty())
                                        emptyStateEvent.postValue(true)
                                    else
                                        onItemsData.postValue(result.genres)

                                }, {
                            loading.postValue(false)
                            showError.postValue(true)
                        }
                        )
        )
    }
}