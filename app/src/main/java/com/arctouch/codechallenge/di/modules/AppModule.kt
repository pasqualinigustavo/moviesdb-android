package com.arctouch.codechallenge.di.modules

import android.content.Context
import com.arctouch.codechallenge.base.ArchtouchApplication
import com.arctouch.codechallenge.rest.APIComm
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: ArchtouchApplication) {

    @Provides
    @Singleton
    fun apiComm() = APIComm.getInstance()

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }
}