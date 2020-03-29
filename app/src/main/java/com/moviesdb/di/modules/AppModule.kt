package com.moviesdb.di.modules

import android.content.Context
import com.moviesdb.application.MoviesDBApplication
import com.moviesdb.rest.APIComm
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MoviesDBApplication) {

    @Provides
    @Singleton
    fun apiComm() = APIComm.getInstance()

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }
}