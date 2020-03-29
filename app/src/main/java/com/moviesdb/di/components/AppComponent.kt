package com.moviesdb.di.components

import android.content.Context
import com.moviesdb.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun context(): Context

    fun inject(holder: ComponentHolder?)
}