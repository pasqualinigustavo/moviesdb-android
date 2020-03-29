package com.moviesdb.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.moviesdb.model.interfaces.ActivityToolbarBehaviour
import com.moviesdb.ui.home.HomeActivity
import com.moviesdb.ui.home.di.HomeComponent

abstract class BaseFragment : Fragment() {

    private var hasContext = false
    var activityToolbarBehaviour: ActivityToolbarBehaviour? = null

    fun getMainComponent(): HomeComponent? {
        val activity = activity
        return if (activity is HomeActivity) {
            activity.component
        } else null
    }

    override fun onDetach() {
        super.onDetach()
        hasContext = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        tryInjection(activity)
        super.onCreate(savedInstanceState)

        if (activity is ActivityToolbarBehaviour) {
            this.activityToolbarBehaviour = activity as ActivityToolbarBehaviour?
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init(savedInstanceState)
    }

    protected fun init(savedInstanceState: Bundle?) {
        this.initComponent(this.view, savedInstanceState)
        this.initData()
        this.initListeners()
    }

    protected abstract fun initComponent(view: View?, savedInstanceState: Bundle?)

    protected abstract fun initListeners()

    protected abstract fun initData()

    abstract fun injectComponents()

    private fun tryInjection(context: Context?) {
        if (!hasContext && context != null) {
            hasContext = true
            injectComponents()
        }
    }

    protected fun setToolbarTitle(title: String) {
        this.activityToolbarBehaviour?.setToolbarTitle(title)
    }

    override fun onStop() {
        super.onStop()
        System.gc()
    }
}
