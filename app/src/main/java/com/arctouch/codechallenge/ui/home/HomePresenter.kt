package com.arctouch.codechallenge.ui.home

import com.arctouch.codechallenge.ui.home.router.HomeRouter

class HomePresenter(private val router: HomeRouter) {

    var mView: HomeView? = null

    fun bindView(view: HomeView) {
        this.mView = view
    }

    fun unbindView() {
        this.mView = null
    }

}