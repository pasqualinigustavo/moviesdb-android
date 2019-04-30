package com.arctouch.codechallenge.di.components

import android.content.Context
import com.arctouch.codechallenge.di.modules.ApiModule
import com.arctouch.codechallenge.di.modules.ApplicationDIModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationDIModule::class, ApiModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun inject(holder: ComponentHolder?)
}
