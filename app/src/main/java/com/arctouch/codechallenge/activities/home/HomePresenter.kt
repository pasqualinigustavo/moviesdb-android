package com.arctouch.codechallenge.activities.home

import com.arctouch.codechallenge.activities.home.router.HomeRouter

class HomePresenter(private val router: HomeRouter) {

    var mView: HomeView? = null

    fun attachView(view: HomeView) {
        this.mView = view
    }

    fun detachView() {
        this.mView = null
    }

}