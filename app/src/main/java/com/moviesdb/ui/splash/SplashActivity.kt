package com.moviesdb.ui.splash

import com.moviesdb.R
import com.moviesdb.ui.BaseActivity
import com.moviesdb.ui.splash.di.DaggerSplashComponent
import com.moviesdb.ui.splash.di.SplashComponent
import com.moviesdb.ui.splash.di.SplashModule
import javax.inject.Inject

class SplashActivity : BaseActivity(R.layout.activity_splash), SplashView {

    companion object {
        val TAG: String = SplashActivity::class.java.simpleName
    }

    val component: SplashComponent by lazy {
        DaggerSplashComponent.builder()
                .parent(appComponent)
                .module(SplashModule())
                .target(this)
                .build()
    }

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun initComponents() {
        presenter.bindView(this)
    }

    override fun initData() {
        presenter.loadGenres()
    }

    override fun initListeners() {

    }

    override fun injectComponents() {
        component.inject(this)
    }
}
