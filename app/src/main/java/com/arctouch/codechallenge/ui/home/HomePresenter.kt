package com.arctouch.codechallenge.ui.home

import com.arctouch.codechallenge.ui.home.router.HomeRouter

class HomePresenter(private val router: HomeRouter) {

    var mView: HomeView? = null

    fun attachView(view: HomeView) {
        this.mView = view
    }

    fun detachView() {
        this.mView = null
    }

}