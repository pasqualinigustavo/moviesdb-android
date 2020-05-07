package com.moviesdb

import android.app.Application
import com.moviesdb.application.MoviesDBApplication
import com.moviesdb.ui.home.di.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, TestAppModule::class, MainActivityModule::class])
interface TestAppComponent {
    @Component.Builder
    interface Builder {

        fun testAppModule(module: TestAppModule): Builder

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(app: MoviesDBApplication)
}