package com.moviesdb.rest

import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber
import java.io.IOException
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
                            Observable.error {
                                asRetrofitException(
                                    throwable,
                                    retrofit
                                )
                            }
                        }
                }
                is Single<*> -> {
                    return (wrapped.adapt(call) as Single<R>)
                        .onErrorResumeNext { throwable: Throwable ->
                            Single.error(
                                asRetrofitException(
                                    throwable,
                                    retrofit
                                )
                            )
                        }
                }
                is Maybe<*> -> {
                    return (wrapped.adapt(call) as Maybe<R>)
                        .onErrorResumeNext { throwable: Throwable ->
                            Maybe.error(
                                asRetrofitException(
                                    throwable,
                                    retrofit
                                )
                            )
                        }
                }
                is Completable -> {
                    return (wrapped.adapt(call) as Completable)
                        .onErrorResumeNext { throwable: Throwable ->
                            Completable.error(
                                asRetrofitException(
                                    throwable,
                                    retrofit
                                )
                            )
                        }
                }
                is Flowable<*> -> {
                    return (wrapped.adapt(call) as Flowable<R>)
                        .onErrorResumeNext { throwable: Throwable ->
                            Flowable.error(
                                asRetrofitException(
                                    throwable,
                                    retrofit
                                )
                            )
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

        private fun asRetrofitException(
            throwable: Throwable,
            retrofit: Retrofit
        ): DefaultException {
            Timber.e(throwable, "RxErrorHandler")
            throwable.printStackTrace()

            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                return DefaultException.httpError(
                    throwable,
                    response.raw().request().url().toString(),
                    response,
                    retrofit
                )
            }
            // A network error happened
            return when (throwable) {
                is IOException -> {
                    DefaultException.networkError(throwable)
                }
                is NullPointerException -> {
                    DefaultException.nullPointerError(throwable)
                }
                else -> DefaultException.unexpectedError(throwable)
            }

            // We don't know what happened. We need to simply convert to an unknown error
        }

    }
}