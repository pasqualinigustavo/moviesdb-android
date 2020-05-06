package com.moviesdb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.moviesdb.R
import com.moviesdb.di.Injectable
import com.moviesdb.model.interfaces.ActivityToolbarBehaviour
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.actionbar.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), ActivityToolbarBehaviour, HasSupportFragmentInjector, Injectable {

    companion object {
        val TAG = HomeActivity::class.java.simpleName
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        setContentView(R.layout.activity_main)

        createActionBar()
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

    fun displayHomeUp() {
        val isRoot = !hasBackStack()

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowHomeEnabled(!isRoot)
            supportActionBar?.setDisplayHomeAsUpEnabled(!isRoot)
            supportActionBar?.setHomeButtonEnabled(!isRoot)
        }
    }

    protected fun hasBackStack(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host)
        if (navHostFragment?.getChildFragmentManager() != null)
            return navHostFragment.getChildFragmentManager().backStackEntryCount > 0
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        for (item in fragments) {
            item?.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host).navigateUp()
    }

    override fun onBackPressed() {
        if (this.hasBackStack())
            findNavController(R.id.nav_host).navigateUp() || super.onSupportNavigateUp()
        else super.onBackPressed()
    }
}
