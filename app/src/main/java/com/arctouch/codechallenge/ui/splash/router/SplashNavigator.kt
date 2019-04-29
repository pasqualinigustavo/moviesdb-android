package com.archtouch.codechallenge.splash.router

import android.content.Intent
import com.arctouch.codechallenge.ui.home.HomeActivity
import com.arctouch.codechallenge.ui.splash.SplashActivity

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