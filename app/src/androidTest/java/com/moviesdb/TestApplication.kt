package com.moviesdb

import androidx.fragment.app.Fragment
import com.moviesdb.application.MoviesDBApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class TestApplication : MoviesDBApplication(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = fragmentInjector

    override fun init() {
    }
}