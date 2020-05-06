package com.moviesdb

import com.moviesdb.rest.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class TestSchedulerProvider : SchedulerProvider {
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}