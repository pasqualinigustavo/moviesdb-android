package com.moviesdb.di

import com.moviesdb.application.MoviesDBApplication
import com.moviesdb.di.components.AppComponent
import com.moviesdb.di.components.DaggerAppComponent
import com.moviesdb.di.modules.AppModule

class ApplicationDependency {

    lateinit var applicationComponent: AppComponent

    fun getApplicationComponent(application: MoviesDBApplication): AppComponent {
        this.applicationComponent = DaggerAppComponent.builder()
                .appModule(AppModule(application))
                .build()
        return applicationComponent
    }

}


