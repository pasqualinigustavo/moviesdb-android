package com.moviesdb.di.components

import android.app.Application
import com.moviesdb.application.MoviesDBApplication
import com.moviesdb.di.modules.AppModule
import com.moviesdb.ui.home.di.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            MainActivityModule::class,
            AndroidInjectionModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MoviesDBApplication)
}