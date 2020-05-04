package com.moviesdb.rest

import io.reactivex.Scheduler

interface SchedulerProvider {
    /**
     * IO thread pool scheduler
     */
    fun io(): Scheduler

    /**
     * Computation thread pool scheduler
     */
    fun computation(): Scheduler

    /**
     * Main Thread scheduler
     */
    fun ui(): Scheduler
}