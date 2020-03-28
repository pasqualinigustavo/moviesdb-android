package com.moviesdb.ui.home

import com.moviesdb.rest.APIComm

class HomeInteractor(private val apiComm: APIComm) {

    companion object {
        val TAG = HomeInteractor::class.java.simpleName
    }

//    fun loadUserData(): Observable<User> {
//        return apiComm.userEndpoint().getMe()
//            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
//    }
}