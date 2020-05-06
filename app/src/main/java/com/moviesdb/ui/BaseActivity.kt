package com.moviesdb.ui

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.moviesdb.R
import java.util.*

abstract class BaseActivity : AppCompatActivity {

    protected val TAG: String

    private var blockOnBackPress = false
    protected var layoutId: Int = 0

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
}

