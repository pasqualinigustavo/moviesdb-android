package com.moviesdb.model.interfaces

interface LifecycleDelegate {

    fun onAppBackgrounded()

    fun onAppForegrounded()
}