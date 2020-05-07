package com.moviesdb.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.moviesdb.R
import com.moviesdb.di.Injectable
import com.moviesdb.ui.home.HomeActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), Injectable {

    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME)
        startActivity(intent)
        finish()
    }

    /***
     *     fun loadGenres(): Observable<GenreResponse> {
    return apiComm.tmdbEndpoint().genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
    .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    fun loadGenres() {
    Log.d(TAG, "loadGenres")
    interactor.loadGenres()
    .subscribe({ response ->
    Cache.genres = response.genres
    launchTheApp()
    }, {
    launchTheApp()
    })
    }
     *
     *
     *
     */
}
