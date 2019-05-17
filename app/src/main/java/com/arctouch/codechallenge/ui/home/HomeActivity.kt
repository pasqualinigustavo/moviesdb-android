package com.arctouch.codechallenge.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.base.AbstractActivity
import com.arctouch.codechallenge.base.ArchtouchApplication
import com.arctouch.codechallenge.base.interfaces.ActivityToolbarBehaviour
import com.arctouch.codechallenge.di.components.ApplicationComponent
import com.arctouch.codechallenge.di.components.DaggerMainComponent
import com.arctouch.codechallenge.di.components.MainComponent
import com.arctouch.codechallenge.ui.home.di.HomeModule
import com.arctouch.codechallenge.ui.movies.MoviesFragment
import kotlinx.android.synthetic.main.actionbar.*
import javax.inject.Inject

class HomeActivity : AbstractActivity(R.layout.activity_main), ActivityToolbarBehaviour, HomeView {

    protected val appComponent: ApplicationComponent
        get() = (application as ArchtouchApplication).getApplicationComponent()

    @Inject
    lateinit var presenter: HomePresenter
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        val TAG = HomeActivity::class.java.simpleName
    }

    val component: MainComponent by lazy {
        DaggerMainComponent.builder()
                .parent(appComponent)
                .module(HomeModule())
                .target(this)
                .build()
    }

    override fun initComponents() {
        component.inject(this)
        presenter.attachView(this)
        navController = Navigation.findNavController(this, R.id.nav_host)
        appBarConfiguration = AppBarConfiguration(navController.graph, null)
        // Set up ActionBar
        createActionBar()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
    }

    override fun initData() {

    }

    override fun injectComponents() {

    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun setToolbarTitle(title: String) {
        if (this.toolbar == null) {
            return
        }
        actionbar_title.setText(title)
        actionbar_title.setVisibility(View.VISIBLE)

        displayHomeUp()
    }

    private fun createActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar.let {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayUseLogoEnabled(true)
            //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left)
        }
    }

    fun displayHomeUp() {
        val isRoot = !this.hasBackStack()
        supportActionBar.let {
            supportActionBar?.setDisplayShowHomeEnabled(!isRoot)
            supportActionBar?.setDisplayHomeAsUpEnabled(!isRoot)
            supportActionBar?.setHomeButtonEnabled(!isRoot)
        }
    }

    override fun initListeners() {
//        toolbar.setNavigationOnClickListener {
//            if (hasBackStack()) {
//                supportFragmentManager.popBackStack()
//            }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        if (fragments != null) {
            for (item in fragments) {
                item?.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
