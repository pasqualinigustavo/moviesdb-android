package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.base.ArchtouchApplication
import com.arctouch.codechallenge.di.components.AppComponent
import com.arctouch.codechallenge.di.components.DaggerAppComponent
import com.arctouch.codechallenge.di.modules.AppModule

class ApplicationDependency {

    lateinit var applicationComponent: AppComponent

    fun getApplicationComponent(application: ArchtouchApplication): AppComponent {
        this.applicationComponent = DaggerAppComponent.builder()
                .appModule(AppModule(application))
                .build()
        return applicationComponent
    }

}


