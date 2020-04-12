package com.moviesdb

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.moviesdb.ui.home.HomeActivity
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

@LargeTest
class HelloWorldEspressoTest {

    companion object {
        @BeforeClass fun setup() {
            // Setting up
        }
    }

    @get:Rule val instantTaskExecutorRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun listGoesOverTheFold() {
        onView(withText("Hello world!")).check(matches(isDisplayed()))
    }
}
