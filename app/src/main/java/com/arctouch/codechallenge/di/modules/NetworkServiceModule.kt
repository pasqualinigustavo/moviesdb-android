package com.arctouch.codechallenge.di.modules

import com.arctouch.codechallenge.rest.endpoints.MoviesEndpoint
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton
//
//@Module(includes = arrayOf(NetworkModule::class))
//class NetworkServiceModule {
//
//    @Provides
//    @Singleton
//    fun provideMoviesEndpoint(retrofit: Retrofit): MoviesEndpoint {
//        return retrofit.create(MoviesEndpoint::class.java)
//    }
//}