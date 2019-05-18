package com.arctouch.codechallenge.di.components

import android.app.Activity
import com.arctouch.codechallenge.di.annotations.PerActivity
import com.arctouch.codechallenge.di.modules.ActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun activity(): Activity
}
