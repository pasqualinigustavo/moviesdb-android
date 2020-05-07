package com.moviesdb.test

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.moviesdb.TestAppModule
import com.moviesdb.TestApplication
import com.moviesdb.TestInjector
import com.moviesdb.data.MockApiComm
import com.moviesdb.ui.home.HomeActivity
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations


open class BaseActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java, true, false)

    val api = MockApiComm()

    @Before
    fun setUp() {

        val instrumentation = getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as TestApplication

        MockitoAnnotations.initMocks(this)
        TestInjector(TestAppModule(app, api), app).inject()
    }

    fun launchApp(loggedIn: Boolean = true) {
        activityRule.launchActivity(Intent())
    }
}

