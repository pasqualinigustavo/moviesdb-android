package com.arctouch.codechallenge.di.modules

import com.arctouch.codechallenge.util.ArchtouchConstants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//@Module
//class NetworkModule {
//
//    @Provides
//    @Singleton
//    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        return loggingInterceptor
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
//        return OkHttpClient.Builder()
//                .retryOnConnectionFailure(true)
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(loggingInterceptor)
//                .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okhttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//                .baseUrl(ArchtouchConstants.URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okhttpClient)
//                .build()
//    }
//}