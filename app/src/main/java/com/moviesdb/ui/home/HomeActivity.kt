package com.moviesdb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.moviesdb.R
import com.moviesdb.di.Injectable
import com.moviesdb.model.interfaces.ActivityToolbarBehaviour
import com.moviesdb.ui.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.actionbar.*
import javax.inject.Inject

class HomeActivity : BaseActivity(R.layout.activity_main), ActivityToolbarBehaviour,
        NavigationView.OnNavigationItemSelectedListener, HasSupportFragmentInjector, Injectable {

    //    @Inject
//    lateinit var presenter: HomePresenter
    private lateinit var navController: NavController

    companion object {
        val TAG = HomeActivity::class.java.simpleName
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun initComponents() {
//        presenter.bindView(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        createActionBar()
        initNavigation()
        navController = Navigation.findNavController(this, R.id.nav_host)

        //setupWithNavController(toolbar, navController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
    }

    override fun initData() {
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

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(findNavController(R.id.nav_host))
//                || super.onOptionsItemSelected(item)
//    }

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
        displayHomeUp()
        return true
    }

//    override fun onBackPressed() {
//        backClicked(navController, appBarConfiguration)
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
