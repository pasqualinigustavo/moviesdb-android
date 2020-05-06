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
    //val errorHandler = MutableLiveData<PageErrorViewModel>()
    val loading = MutableLiveData<Boolean>()
    val disposables = CompositeDisposable()

    private var isAllowedToAccessTheDashboard = false

    override fun onCleared() {
        disposables.clear()
    }

    open fun onAttached() {

    }

    fun onDetach() {

    }

    fun onFragmentResult(data: Serializable) {

    }

    //    fun showNetworkError(retry: () -> Unit) {
//        recoverableError.value = PageErrorViewModel(PageErrorType.NETWORK, retry)
//    }
//
    fun hideNetworkError() {
//        recoverableError.value = PageErrorViewModel(PageErrorType.NO_ERROR)
    }
//
//    fun showError(errorType: PageErrorType) {
//        recoverableError.value = PageErrorViewModel(errorType)
//    }
//
//    fun showFullScreenError(retryHandler: (() -> Unit)) {
//        navigator.dispatchNavigationEvent(
//            Navigator.NavigationEvent.FULL_SCREEN_ERROR,
//            FullScreenErrorViewModel({
//                retryHandler()
//            })
//        )
//    }
//
//    fun showErrorDialog(errorTitle: String, errorContent: String, retryHandler: (() -> Unit)?) {
//        navigator.dispatchNavigationEvent(
//            Navigator.NavigationEvent.ERROR_DIALOG,
//            ErrorDialogViewModel(
//                errorTitle,
//                errorContent
//            ) { retryHandler?.invoke() }
//        )
//    }
}

