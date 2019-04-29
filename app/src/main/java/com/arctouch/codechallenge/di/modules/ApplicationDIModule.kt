package com.arctouch.codechallenge.di.modules

import android.content.Context
import com.arctouch.codechallenge.base.ArchtouchApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationDIModule(private val application: ArchtouchApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }
}