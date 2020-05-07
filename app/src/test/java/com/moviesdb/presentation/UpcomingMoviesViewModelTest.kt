package com.moviesdb.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moviesdb.TestSchedulerProvider
import com.moviesdb.app.UpcomingMoviesRepository
import com.moviesdb.application.AnalyticsProvider
import com.moviesdb.domain.GetUpcomingMoviesUsecase
import com.moviesdb.model.Movie
import com.moviesdb.model.UpcomingMoviesResponse
import com.moviesdb.navigator.Navigator
import com.moviesdb.navigator.SharedEvents
import com.moviesdb.ui.home.router.HomeNavigator
import com.moviesdb.ui.movies.UpcomingMoviesViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UpcomingMoviesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    var sharedEvents: SharedEvents = mock()
    var navigator: Navigator = spy(
            HomeNavigator(
                    sharedEvents
            )
    )

    val analyticsProvider: AnalyticsProvider = mock()
    val remoteMessagesRepository: UpcomingMoviesRepository = mock()
    val getNotificationUseCase: GetUpcomingMoviesUsecase = mock()

    lateinit var viewModel: UpcomingMoviesViewModel

    @Before
    fun init() {
        val behaviorSubject: BehaviorSubject<UpcomingMoviesResponse> = BehaviorSubject.create()
        behaviorSubject.onNext(UpcomingMoviesResponse(1, ArrayList<Movie>(), any(), any()))
        whenever(remoteMessagesRepository.getUpcomingMovies(1)).thenReturn(behaviorSubject)
        viewModel = UpcomingMoviesViewModel(
                schedulerProviderFacade = TestSchedulerProvider(),
                analyticsProvider = analyticsProvider,
                getUpcomingMoviesUsecase = getNotificationUseCase,
                navigator = navigator)
    }

    @Test
    fun onMovieClickedGoToDetails() {
        val messageId = "test"
//        val testMessage =
//                Movie(
//                        messageId,
//                        Date(),
//                        read = false,
//                        archived = false,
//                        message = "test message",
//                        header = "test title"
//                )
//        whenever(remoteMessagesRepository.setMessageRead(messageId)).thenReturn(
//                io.reactivex.Observable.just(MessageIsRead(Data("success"), null))
//        )
        //viewModel.onItemClicked(testMessage)
//        Assert.assertEquals(testMessage.read, true)
    }

}