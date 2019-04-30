package com.arctouch.codechallenge.base

import android.os.Bundle
import com.arctouch.codechallenge.di.components.ApplicationComponent
import com.arctouch.codechallenge.di.modules.ActivityModule

open class BaseActivity : AbstractActivity {

    protected val appComponent: ApplicationComponent
        get() = (application as ArchtouchApplication).applicationComponent

    protected val activityModule: ActivityModule
        get() = ActivityModule(this)

    constructor(layoutId: Int, viewContentId: Int) : super(layoutId, viewContentId) {}

    constructor(layoutId: Int) : super(layoutId) {}

    override fun initComponents() {

    }

    override fun initFragments(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

    override fun initListeners() {

    }

    override fun injectComponents() {

    }
}
