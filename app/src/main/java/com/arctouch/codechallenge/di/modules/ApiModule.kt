package com.arctouch.codechallenge.di.modules

import com.arctouch.codechallenge.rest.APIComm
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun apiComm() = APIComm.getInstance()
}