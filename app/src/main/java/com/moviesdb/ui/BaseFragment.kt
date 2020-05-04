package com.moviesdb.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviesdb.model.interfaces.ActivityToolbarBehaviour
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    var activityToolbarBehaviour: ActivityToolbarBehaviour? = null

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity is ActivityToolbarBehaviour) {
            this.activityToolbarBehaviour = activity as ActivityToolbarBehaviour?
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        if (toolbar != null) {
//            (activity as AppCompatActivity).setSupportActionBar(toolbar as Toolbar)
//            setupToolbar()
//            setHasOptionsMenu(true)
//        }
//
//        loading = view.findViewById(R.id.progress_bar)
//        notificationLayout = view.findViewById(R.id.notification_layout)
//
//        viewModel = createViewModel()
//        viewModel.navigator.observeEvents(this, navController())
//        viewModel.params.value = arguments?.getSerializable(NavigationController.DATA_KEY)
//        viewModel.loading.observe(viewLifecycleOwner, Observer { showProgressBar(it) })
//        viewModel.recoverableError.observe(viewLifecycleOwner, Observer { showRecoverableError(it) })
//        viewModel.onAttached()

        //hideKeyboard()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
//                        val isThereSomethingToPop = navController().finish()
//                        if (!isThereSomethingToPop) {
//                            activity?.finish()
//                        }
                    }
                }
        )
    }

    protected fun init(savedInstanceState: Bundle?) {
        this.initComponent(this.view, savedInstanceState)
        this.initData()
        this.initListeners()
    }

    protected abstract fun initComponent(view: View?, savedInstanceState: Bundle?)

    protected abstract fun initListeners()

    protected abstract fun initData()

    protected fun setToolbarTitle(title: String) {
        this.activityToolbarBehaviour?.setToolbarTitle(title)
    }

    override fun onStop() {
        super.onStop()
        System.gc()
    }
}
