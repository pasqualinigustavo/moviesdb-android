package com.moviesdb.ui

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.moviesdb.R
import java.util.*

abstract class BaseActivity : AppCompatActivity {

    protected val TAG: String

    private var tryToClose: Date? = null
    private var blockOnBackPress = false
    protected var layoutId: Int = 0
    protected var questionBeforeClose = true

    constructor(layoutId: Int) : super() {
        this.TAG = this.javaClass.simpleName

        this.layoutId = layoutId
    }

    protected abstract fun initComponents()

    protected abstract fun initData()

    protected abstract fun initListeners()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.layoutId)

        this.init()
    }

    protected fun init() {
        this.blockOnBackPress = false
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

    override fun setTitle(@StringRes title: Int) {
        actionBar?.setTitle(title)
        supportActionBar?.setTitle(title)
    }

//    fun backClicked(navController: NavController, appBarConfiguration: AppBarConfiguration) {
//        onSupportNavigateUp(navController, appBarConfiguration, true)
//    }

//    fun onSupportNavigateUp(
//            navController: NavController,
//            appBarConfiguration: AppBarConfiguration,
//            onBackPressed: Boolean
//    ): Boolean {
//        if (this.blockOnBackPress) {
//            return false
//        }
//
//        if (!questionBeforeClose) {
//            if (!onBackPressed)
//                return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//            else super.onBackPressed()
//        }
//
//        if (this.hasBackStack()) {
//            if (!onBackPressed)
//                return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//            else {
//                super.onBackPressed()
//                return false
//            }
//        }
//
//        if (this.tryToClose != null) {
//            val tryToCloseAgain = Date()
//            val diff = tryToCloseAgain.time - tryToClose!!.time
//            val diffSec = diff / 1000
//
//            if (diffSec > 1) {
//                this.tryToClose = null
//            } else {
//                if (!onBackPressed)
//                    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//                else super.onBackPressed()
//            }
//        }
//
//        this.tryToClose = Date()
//        Toast.makeText(this, getString(R.string.message_close_app), Toast.LENGTH_SHORT).show()
//        return false
//    }
}

