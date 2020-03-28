package com.moviesdb.ui

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.moviesdb.R
import com.moviesdb.application.MoviesDBApplication
import com.moviesdb.di.components.AppComponent
import com.moviesdb.model.interfaces.ActivityBehaviour
import java.util.*

abstract class BaseActivity : AppCompatActivity, ActivityBehaviour {

    protected val TAG: String
    protected val appComponent: AppComponent
        get() = (application as MoviesDBApplication).getApplicationComponent()

    private var tryToClose: Date? = null
    private var blockOnBackPress = false
    protected var layoutId: Int = 0
    protected var progressDialog: MaterialDialog? = null
    protected var questionBeforeClose = true

    constructor(layoutId: Int) : super() {
        this.TAG = this.javaClass.simpleName

        this.layoutId = layoutId
    }

    protected abstract fun initComponents()

    protected abstract fun initData()

    protected abstract fun initListeners()

    protected abstract fun injectComponents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.layoutId)

        this.init()
    }

    protected fun init() {
        this.blockOnBackPress = false
        this.injectComponents()
        this.initComponents()
        this.initListeners()
        this.initData()
    }

    protected fun hasBackStack(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host)
        if (navHostFragment?.getChildFragmentManager() != null)
            return navHostFragment.getChildFragmentManager().backStackEntryCount > 0
        return false
    }

    override fun showProgressBar() {
        progressDialog?.let {
            if (!it.isShowing)
                createDialog()
        }
        if (progressDialog == null) {
            createDialog()
        }
    }

    private fun createDialog(): MaterialDialog? {
        val b = MaterialDialog.Builder(this)
        b.title(R.string.app_name)
        b.cancelable(false)
        b.theme(Theme.LIGHT)
        b.content(R.string.label_please_wait)
        b.progress(true, 0)
        progressDialog = b.show()
        return progressDialog
    }

    override fun hideProgressBar() {
        progressDialog?.let {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    override fun setTitle(@StringRes title: Int) {
        actionBar?.setTitle(title)
        supportActionBar?.setTitle(title)
    }

    fun backClicked(navController: NavController, appBarConfiguration: AppBarConfiguration) {
        onSupportNavigateUp(navController, appBarConfiguration, true)
    }

    fun onSupportNavigateUp(
            navController: NavController,
            appBarConfiguration: AppBarConfiguration,
            onBackPressed: Boolean
    ): Boolean {
        if (this.blockOnBackPress) {
            return false
        }

        if (!questionBeforeClose) {
            if (!onBackPressed)
                return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
            else super.onBackPressed()
        }

        if (this.hasBackStack()) {
            if (!onBackPressed)
                return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
            else {
                super.onBackPressed()
                return false
            }
        }

        if (this.tryToClose != null) {
            val tryToCloseAgain = Date()
            val diff = tryToCloseAgain.time - tryToClose!!.time
            val diffSec = diff / 1000

            if (diffSec > 1) {
                this.tryToClose = null
            } else {
                if (!onBackPressed)
                    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
                else super.onBackPressed()
            }
        }

        this.tryToClose = Date()
        Toast.makeText(this, getString(R.string.message_close_app), Toast.LENGTH_SHORT).show()
        return false
    }
}

