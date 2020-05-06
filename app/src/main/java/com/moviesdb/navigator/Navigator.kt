package com.moviesdb.navigator

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.moviesdb.util.SingleLiveEvent
import java.io.Serializable

abstract class Navigator(val sharedEvents: SharedEvents) {

    val navigationEvent: MutableLiveData<NavigationEventData>
        get() {
            return sharedEvents.navigationEvent
        }

    private val navigationResultEvent: SingleLiveEvent<Serializable>
        get() {
            return sharedEvents.navigationResultEvent
        }

    fun setFinishData(data: Serializable?) {
        data?.let {
            sharedEvents.navigationResultEvent.value = data
        }
    }

    fun observeEvents(owner: LifecycleOwner, navController: NavigationController) {
        navigationEvent.observe(owner, Observer {
            if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                if (it != null && handleNavigationEvent(it, navController)) {
                    navigationEvent.value = null
                }
        })
    }

    fun observeNavigationResult(owner: LifecycleOwner, observer: Observer<in Serializable>) {
        navigationResultEvent.observe(owner, observer)
    }

    fun showFullScreenError(data: Serializable, navController: NavigationController) {
//        val model = data as FullScreenErrorViewModel
//        val exitAction = model.exitAction?.let { it } ?: {
//            navController.finish()
//        }
//        navController.showDialog(
//            FullScreenErrorDialog(
//                model.recoveryAction,
//                exitAction
//            )
//        )
    }

    fun showErrorDialog(data: Serializable, navController: NavigationController) {
//        val model = data as ErrorDialogViewModel
//        val exitAction = model.exitAction?.let { it } ?: {
//            navController.finish()
//        }
//        navController.showDialog(
//            ErrorDialog(
//                model.errorTitle,
//                model.errorContent,
//                exitAction
//            )
//        )
    }

    abstract fun handleNavigationEvent(
            navigationState: NavigationEventData,
            navController: NavigationController
    ): Boolean

    fun dispatchNavigationEvent(event: NavigationEvent) {
        navigationEvent.value =
                NavigationEventData(event)
    }

    fun dispatchNavigationEvent(event: NavigationEvent, data: Serializable) {
        navigationEvent.value =
                NavigationEventData(event, data)
    }

    fun navigate(locationId: Int, navController: NavigationController, data: Serializable? = null) {
        sharedEvents.navigationEvent.value = NavigationEventData(NavigationEvent.NOP)
        locationId.takeIf { it != navController.currentId }
                ?.let {
                    navController.navigate(it, data)
                }
    }

    fun finish(navController: NavigationController): Boolean {
        return navController.finish()
    }

    fun openWebBrowser(navController: NavigationController, url: String) {
        navController.openWebBrowser(url)
    }

    data class NavigationEventData(val event: NavigationEvent, val data: Serializable? = null)

    enum class NavigationEvent {
        NOP,
        MOVIE_DETAILS,
        POP_BACKSTACK
    }

    companion object {
        const val DASHBOARD = "dash"
    }
}
