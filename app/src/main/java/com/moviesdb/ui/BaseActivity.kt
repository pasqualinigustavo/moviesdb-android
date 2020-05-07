package com.moviesdb.ui

import android.os.Bundle
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
        viewModel.onAttached()
    }
}

