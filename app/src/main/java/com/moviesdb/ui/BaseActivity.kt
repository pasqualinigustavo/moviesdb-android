package com.moviesdb.ui

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.moviesdb.di.Injectable
import javax.inject.Inject

abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity, Injectable {

    protected val TAG: String
    protected var layoutId: Int = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ViewModel


    constructor(layoutId: Int) : super() {
        this.TAG = this.javaClass.simpleName
        this.layoutId = layoutId
    }

    protected abstract fun setupObservers()

    abstract fun createViewModel(): ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        init()
    }

    protected fun init() {
        viewModel = createViewModel()
        setupObservers()
        //viewModel.params.value = arguments?.getSerializable(NavigationController.DATA_KEY)
        viewModel.onAttached()
    }
}

