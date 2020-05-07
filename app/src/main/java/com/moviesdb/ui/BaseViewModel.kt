package com.moviesdb.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.navigator.Navigator
import com.moviesdb.rest.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable

abstract class BaseViewModel
constructor(
        val schedulerProviderFacade: SchedulerProvider,
        val analyticsProvider: AnalyticsProvider,
        val navigator: Navigator
) :
        ViewModel() {

    val params = MutableLiveData<Serializable>()
    val loading = MutableLiveData<Boolean>()
    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

    open fun onAttached() {

    }

    fun onDetach() {

    }

    fun onFragmentResult(data: Serializable) {

    }
}

