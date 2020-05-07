package com.moviesdb.mock

import io.reactivex.Observable
import okhttp3.Headers
import okhttp3.ResponseBody
import org.mockito.Mockito.mock
import retrofit2.Response


object MockRetrofitResponse {

    fun <T> newError(httpStatusCode: Int): Observable<T> {
        return Observable.create {
            val response: Response<T> = Response.error(httpStatusCode, mock(ResponseBody::class.java))
            it.onNext(response.body()!!)
        }
    }

    fun <T> newError(
        rawResponseBuilder: okhttp3.Response.Builder,
        errorBody: ResponseBody
    ): Observable<okhttp3.ResponseBody> {
        return Observable.create {
            val response = Response.error<T>(errorBody, rawResponseBuilder.build())
            it.onNext(response.errorBody()!!)
        }
    }

    fun <T> newNetworkError(): Observable<T> {
        return Observable.create {
            it.onError(Throwable())
        }
    }

    fun <T> newSuccess(body: T): Observable<T> {
        return Observable.create {
            val success = Response.success(body)
            it.onNext(success.body()!!)
        }
    }

    fun <T> newSuccess(response: T, headers: Headers): Observable<T> {
        return Observable.create {
            val success = Response.success(response, headers)
            it.onNext(success.body()!!)
        }
    }

}
