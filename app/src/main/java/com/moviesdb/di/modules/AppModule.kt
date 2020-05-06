package com.moviesdb.di.modules

import android.app.Application
import android.content.Context
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.application.AndroidAnalyticsProvider
import com.moviesdb.application.MoviesDBApplication
import com.moviesdb.di.ViewModelModule
import com.moviesdb.navigator.Navigator
import com.moviesdb.navigator.SharedEvents
import com.moviesdb.rest.APIComm
import com.moviesdb.rest.SchedulerProvider
import com.moviesdb.rest.SchedulersFacade
import com.moviesdb.ui.home.router.HomeNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun apiComm() = APIComm.getInstance()

    @Singleton
    @Provides
    fun provideScheduleProvider(): SchedulerProvider {
        return SchedulersFacade()
    }

    @Singleton
    @Provides
    fun provideAnalytics(app: Application): AnalyticsProvider {
        return AndroidAnalyticsProvider(app)
    }

    @Singleton
    @Provides
    fun provideApp(app: Application): MoviesDBApplication {
        return app as MoviesDBApplication
    }

    @Singleton
    @Provides
    @Named(Navigator.DASHBOARD)
    fun provideDashboardNavigator(sharedEvents: SharedEvents): Navigator {
        return HomeNavigator(sharedEvents)
    }

    @Singleton
    @Provides
    fun bindSharedEvents(): SharedEvents {
        return SharedEvents()
    }
}