package com.arctouch.codechallenge.ui.splash

import android.os.Bundle
import android.widget.Toast
import com.archtouch.codechallenge.splash.SplashView
import com.archtouch.codechallenge.splash.di.DaggerSplashComponent
import com.archtouch.codechallenge.splash.di.SplashComponent
import com.archtouch.codechallenge.splash.di.SplashModule
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.BaseActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(R.layout.splash_activity), SplashView {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        component.inject(this)

        presenter.attachView(this)
        presenter.init()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
