package com.moviesdb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import com.moviesdb.R
import com.moviesdb.model.interfaces.ActivityToolbarBehaviour
import com.moviesdb.ui.BaseActivity
import com.moviesdb.ui.home.di.DaggerHomeComponent
import com.moviesdb.ui.home.di.HomeComponent
import com.moviesdb.ui.home.di.HomeModule
import kotlinx.android.synthetic.main.actionbar.*
import javax.inject.Inject

class HomeActivity : BaseActivity(R.layout.activity_main), ActivityToolbarBehaviour, HomeView,
        NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var presenter: HomePresenter
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        val TAG = HomeActivity::class.java.simpleName
    }

    val component: HomeComponent by lazy {
        DaggerHomeComponent.builder()
                .parent(appComponent)
                .module(HomeModule())
                .target(this)
                .build()
    }

    override fun initComponents() {
        presenter.bindView(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        createActionBar()
        initNavigation()
        navController = Navigation.findNavController(this, R.id.nav_host)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setupWithNavController(toolbar, navController, appBarConfiguration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
    }

    override fun initData() {
    }

    override fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun injectComponents() {
        component.inject(this)
    }

    override fun onDestroy() {
        presenter.unbindView()
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
        }
    }

    private fun initNavigation() {
        //nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host))
                || super.onOptionsItemSelected(item)
    }

    fun closeMenu() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    fun displayHomeUp() {
        val isRoot = !this.hasBackStack()
        //drawerToggle?.setDrawerIndicatorEnabled(isRoot)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowHomeEnabled(!isRoot)
            supportActionBar?.setDisplayHomeAsUpEnabled(!isRoot)
            supportActionBar?.setHomeButtonEnabled(!isRoot)
        }

        if (isRoot) {
            //this.drawerToggle?.syncState()
        }
    }

    override fun initListeners() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (item in fragments) {
            item?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        displayHomeUp()
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            closeMenu()
            return
        }
        backClicked(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
