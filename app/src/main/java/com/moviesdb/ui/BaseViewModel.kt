package com.moviesdb.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.rest.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable

abstract class BaseViewModel
constructor(
        val schedulerProviderFacade: SchedulerProvider,
        val analyticsProvider: AnalyticsProvider
) :
        ViewModel() {

//    @Inject
//    lateinit var logoutUsecase: LogoutUsecase
//
//    private final val isAuthenticated =
//        this.javaClass.getAnnotation(Authenticated::class.java) != null

    final val params = MutableLiveData<Serializable>()

    final val loading = MutableLiveData<Boolean>()
    //    final val recoverableError = MutableLiveData<PageErrorViewModel>()
    final val disposables = CompositeDisposable()

    private var isAllowedToAccessTheDashboard = false

    fun isAllowedToAccessTheDashboard() = isAllowedToAccessTheDashboard

//    final fun ifAuthenticated(authFunc: () -> Unit) {
//        if (authRepository.isAuthenticated()) {
//            authFunc()
//        }
//    }

    override fun onCleared() {
        disposables.clear()
    }

    open fun onAttached() {
//        checkIfUserShouldStartTheLogin()
//        recoverableError.value = PageErrorViewModel(PageErrorType.NO_ERROR)
    }

//    fun checkIfUserShouldStartTheLogin() {
//        val shouldStartTheLogin = isAuthenticated
//                && !authRepository.isAuthenticated()
//                && !navigator.inLoginFlow
//
//        if (shouldStartTheLogin)
//            navigator.dispatchNavigationEvent(Navigator.NavigationEvent.START_LOGIN)
//
//        isAllowedToAccessTheDashboard = !shouldStartTheLogin
//    }

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

    fun onLogout() {
//        disposables.add(
//            logoutUsecase.execute()
//                .applySchedulers(schedulerProviderFacade)
//                .subscribe {
//                    navigator.dispatchNavigationEvent(Navigator.NavigationEvent.LOGOUT)
//                }
//        )
    }
}

