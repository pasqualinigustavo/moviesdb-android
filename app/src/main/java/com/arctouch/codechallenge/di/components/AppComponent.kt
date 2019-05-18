package com.arctouch.codechallenge.di.components

import android.content.Context
import com.arctouch.codechallenge.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun context(): Context

    fun inject(holder: ComponentHolder?)
}