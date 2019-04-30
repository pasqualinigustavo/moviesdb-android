package com.arctouch.codechallenge.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.interfaces.ActivityBehaviour
import com.arctouch.codechallenge.base.interfaces.ActivityToolbarBehaviour
import com.arctouch.codechallenge.di.components.MainComponent
import com.arctouch.codechallenge.ui.home.HomeActivity

abstract class BaseFragment() : Fragment() {

    var activityBehaviour: ActivityBehaviour? = null
    var activityToolbarBehaviour: ActivityToolbarBehaviour? = null

    private var progressDialog: MaterialDialog? = null
    private var hasContext = false

    fun getMainComponent(): MainComponent? {
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

        if (activity is ActivityBehaviour) {
            this.activityBehaviour = activity as ActivityBehaviour?
        }

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

    override fun onDestroy() {
        super.onDestroy()

        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.message, e)
        }

    }

    fun showProgressDialog() {
        if (context != null) {
            val b = MaterialDialog.Builder(context!!)
            b.title(R.string.label_please_wait)
            b.cancelable(false)
            b.theme(Theme.LIGHT)
            b.content(R.string.label_loading)
            b.progress(true, 0)
            progressDialog = b.show()
        }
    }

    fun hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.hide()
        }
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    protected fun switchContent(fragmentClass: BaseFragment, addToBackStack: Boolean) {
        if (this.activityBehaviour != null) {
            activityBehaviour?.switchContent(fragmentClass, addToBackStack)
        }
    }

    protected fun backFragment() {
        if (this.activityBehaviour != null) {
            activityBehaviour?.backFragment()
        }
    }
}
