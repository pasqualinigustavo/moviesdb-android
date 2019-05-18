package com.arctouch.codechallenge.base

import com.arctouch.codechallenge.di.components.AppComponent
import com.arctouch.codechallenge.di.modules.ActivityModule

open class BaseActivity : AbstractActivity {

    protected val appComponent: AppComponent
        get() = (application as ArchtouchApplication).getApplicationComponent()

    protected val activityModule: ActivityModule
        get() = ActivityModule(this)

    constructor(layoutId: Int, viewContentId: Int) : super(layoutId, viewContentId) {}

    constructor(layoutId: Int) : super(layoutId) {}

    override fun initComponents() {

    }

    override fun initData() {

    }

    override fun initListeners() {

    }

    override fun injectComponents() {

    }
}
