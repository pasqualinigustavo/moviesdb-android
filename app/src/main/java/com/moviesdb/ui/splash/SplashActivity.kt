package com.moviesdb.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.moviesdb.R
import com.moviesdb.splash.SplashView
import com.moviesdb.splash.di.DaggerSplashComponent
import com.moviesdb.splash.di.SplashComponent
import com.moviesdb.splash.di.SplashModule
import com.moviesdb.ui.BaseActivity
import com.moviesdb.ui.home.HomeActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(R.layout.activity_splash), SplashView {

    companion object {
        val TAG: String = SplashActivity::class.java.simpleName
    }

//    @Inject
//    lateinit var presenter: SplashPresenter

    override fun initComponents() {
//        presenter.bindView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
    }

    override fun initData() {
//        presenter.loadGenres()

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME)
        startActivity(intent)
        finish()
    }

    override fun initListeners() {

    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
//        presenter.unbindView()
    }
}
