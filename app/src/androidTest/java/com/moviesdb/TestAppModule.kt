package com.moviesdb

import android.app.Application
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.application.MoviesDBApplication
import com.moviesdb.data.MockApiComm
import com.moviesdb.di.ViewModelModule
import com.moviesdb.navigator.Navigator
import com.moviesdb.navigator.SharedEvents
import com.moviesdb.rest.APIComm
import com.moviesdb.rest.SchedulerProvider
import com.moviesdb.ui.home.router.HomeNavigator
import dagger.Module

import dagger.Provides
import org.mockito.Mockito.mock
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class TestAppModule(
        private val app: Application,
        private val api: MockApiComm = MockApiComm(),
        private val scheduler: SchedulerProvider = TestSchedulerProvider()
) {

    @Singleton
    @Provides
    fun provideApi(): MockApiComm {
        return api
    }

    @Singleton
    @Provides
    fun provideApp(app: Application): MoviesDBApplication {
        return app as MoviesDBApplication
    }

    @Singleton
    @Provides
    fun provideScheduleProvider(): SchedulerProvider {
        return scheduler
    }

    @Provides
    @Named(Navigator.DASHBOARD)
    fun provideDashboardNavigator(sharedEvents: SharedEvents): Navigator {
        return HomeNavigator(sharedEvents)
    }

    @Provides
    fun provideAnalytics(app: Application): AnalyticsProvider {
        return mock(AnalyticsProvider::class.java)
    }

    @Singleton
    @Provides
    fun bindSharedEvents(): SharedEvents {
        return SharedEvents()
    }
}