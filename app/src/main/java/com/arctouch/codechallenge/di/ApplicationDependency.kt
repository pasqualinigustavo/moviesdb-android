package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.base.ArchtouchApplication
import com.arctouch.codechallenge.di.components.ApplicationComponent
import com.arctouch.codechallenge.di.components.DaggerApplicationComponent
import com.arctouch.codechallenge.di.modules.ApplicationDIModule

class ApplicationDependency {

    lateinit var applicationComponent: ApplicationComponent

    fun getApplicationComponent(application: ArchtouchApplication): ApplicationComponent? {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationDIModule(ApplicationDIModule(application))
                .build()
        return applicationComponent
    }

}


