package com.moviesdb.splash.router

import android.content.Intent
import com.moviesdb.ui.home.HomeActivity
import com.moviesdb.ui.splash.SplashActivity

class SplashNavigator(private val activity: SplashActivity) {

    companion object {
        private val TAG = SplashNavigator::class.java.simpleName
    }

    fun openMainActivity() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME)
        activity.startActivity(intent)
        activity.finish()
    }
}