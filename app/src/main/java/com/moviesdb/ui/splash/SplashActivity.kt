package com.moviesdb.ui.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.moviesdb.R
import com.moviesdb.rest.endpoint.TmdbApi
import com.moviesdb.data.Cache
import com.moviesdb.ui.BaseActivity
import com.moviesdb.ui.splash.di.DaggerSplashComponent
import com.moviesdb.ui.splash.di.SplashComponent
import com.moviesdb.ui.splash.di.SplashModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

//class SplashActivity : BaseActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//
//        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                Cache.cacheGenres(it.genres)
//                startActivity(Intent(this, HomeActivity::class.java))
//                finish()
//            }
//    }
//}
