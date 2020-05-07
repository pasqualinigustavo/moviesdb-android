package com.moviesdb.ui.splash

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moviesdb.R
import com.moviesdb.data.Cache
import com.moviesdb.ui.BaseActivity
import com.moviesdb.ui.home.HomeActivity

class SplashActivity : BaseActivity<SplashActivityViewModel>(R.layout.activity_splash) {

    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }

    override fun setupObservers() {
        viewModel.onItemsData.value = emptyList()
        viewModel.onItemsData.observe(
                this,
                Observer {
                    Cache.genres = it
                    openHomeActivity()
                }
        )

        viewModel.emptyStateEvent.observe(
                this,
                Observer {
                    openHomeActivity()
                }
        )
    }

    override fun createViewModel(): SplashActivityViewModel {
        return ViewModelProvider(this, viewModelFactory)
                .get(SplashActivityViewModel::class.java)
    }

    fun openHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME)
        startActivity(intent)
        finish()
    }

}
