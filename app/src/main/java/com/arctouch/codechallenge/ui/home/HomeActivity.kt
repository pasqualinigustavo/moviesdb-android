package com.arctouch.codechallenge.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.Window
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

class HomeActivity : AbstractActivity(R.layout.activity_main, R.id.activity_content), ActivityToolbarBehaviour, HomeView {

    protected val appComponent: ApplicationComponent
        get() = (application as ArchtouchApplication).applicationComponent

    @Inject
    lateinit var presenter: HomePresenter

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
        createActionBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        super.onCreate(savedInstanceState)
    }

    override fun initData() {

    }

    override fun initFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            clearBackStackWhenActivityWasKilled()
            switchContent(MoviesFragment.newInstance(), this.viewContentId, false)
        }
    }

    fun clearBackStackWhenActivityWasKilled() {
        if (this.hasBackStack()) {
            supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun initListeners() {
        toolbar.setNavigationOnClickListener {
            if (hasBackStack()) {
                supportFragmentManager.popBackStack()
            }
        }
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
        if (hasBackStack()) {
            supportFragmentManager.popBackStack()
        }
        return true
    }
}
