package com.moviesdb.rest

import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type


class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val wrapped = original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(retrofit, wrapped)
    }

    private class RxCallAdapterWrapper<R>(
            val retrofit: Retrofit,
            val wrapped: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {
        override fun responseType(): Type {
            return wrapped.responseType()
        }

        @Suppress("UNCHECKED_CAST")

        override fun adapt(call: Call<R>): Any {
            wrapped.adapt(call)
            val result = wrapped.adapt(call)
            when (result) {
                is Observable<*> -> {
                    return (wrapped.adapt(call) as Observable<R>)
                            .onErrorResumeNext { throwable: Throwable ->
                                Observable.error { throwable }
                            }
                }
                is Single<*> -> {
                    return (wrapped.adapt(call) as Single<R>)
                            .onErrorResumeNext { throwable: Throwable ->
                                Single.error(throwable)
                            }
                }
                is Maybe<*> -> {
                    return (wrapped.adapt(call) as Maybe<R>)
                            .onErrorResumeNext { throwable: Throwable ->
                                Maybe.error(throwable)
                            }
                }
                is Completable -> {
                    return (wrapped.adapt(call) as Completable)
                            .onErrorResumeNext { throwable: Throwable ->
                                Completable.error(throwable)
                            }
                }
                is Flowable<*> -> {
                    return (wrapped.adapt(call) as Flowable<R>)
                            .onErrorResumeNext { throwable: Throwable ->
                                Flowable.error(throwable)
                            }
                }
                else -> return result
            }
        }
    }

    companion object {

        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }

    }
}