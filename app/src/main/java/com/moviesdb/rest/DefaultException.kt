package com.moviesdb.rest

import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.HttpURLConnection


class DefaultException(
        val msg: String?,
        private val exceptionURL: String?,
        private val exceptionResponse: Response<*>?,
        private val exceptionKind: Kind,
        private val exception: Throwable,
        private val exceptionRetrofit: Retrofit?
) : RuntimeException() {

    private var errorBody: String? = null

    init {
        if (exception is HttpException) {
            errorBody = exception.response().errorBody()?.string()
        }
    }

    companion object {

        const val INTERNAL_SERVER_ERROR_HTTP_CODE = 500

        fun httpError(
                throwable: Throwable,
                url: String,
                response: Response<*>,
                retrofit: Retrofit
        ): DefaultException {
            val message = response.code().toString() + " " + response.message()
            return DefaultException(
                    message,
                    url,
                    response,
                    Kind.fromStatusCode(response.code()),
                    throwable,
                    retrofit
            )
        }

        fun networkError(exception: IOException): DefaultException {
            return DefaultException(exception.message, null, null, Kind.NETWORK, exception, null)
        }

        fun unexpectedError(exception: Throwable): DefaultException {
            return DefaultException(exception.message, null, null, Kind.UNEXPECTED, exception, null)
        }

        fun nullPointerError(exception: NullPointerException): DefaultException {
            return DefaultException(null, null, null, Kind.NULL_POINTER, exception, null)
        }
    }

    enum class Kind {
        NETWORK,
        HTTP,
        UNEXPECTED,
        UNAUTHORIZED,
        NOT_LINKED,
        NULL_POINTER,
        FORBIDDEN;

        companion object {
            fun fromStatusCode(code: Int): Kind {
                return when (code) {
                    HttpURLConnection.HTTP_FORBIDDEN -> FORBIDDEN
                    HttpURLConnection.HTTP_UNAUTHORIZED -> UNAUTHORIZED
                    410, 412 -> NOT_LINKED
                    in 500..599 -> NETWORK
                    else -> HTTP
                }
            }
        }
    }

    fun getUrl(): String? {
        return exceptionURL
    }

    fun getResponse(): Response<*>? {
        return exceptionResponse
    }

    fun getKind(): Kind {
        return exceptionKind
    }

    fun getRetrofit(): Retrofit? {
        return exceptionRetrofit
    }

    fun isTimeout(): Boolean {
        return getKind() == Kind.NETWORK
    }

    fun isBadRequest(): Boolean {
        return getKind() == Kind.HTTP
    }

    fun isForbidden(): Boolean {
        return getKind() == Kind.FORBIDDEN
    }

    fun isUnauthorized(): Boolean {
        return getKind() == Kind.UNAUTHORIZED
    }

    fun isNotLinked(): Boolean {
        return getKind() == Kind.NOT_LINKED
    }

    fun isUnexpected(): Boolean {
        return getKind() == Kind.UNEXPECTED
    }

    fun isAnInternalServerError() = (exceptionResponse?.code() == INTERNAL_SERVER_ERROR_HTTP_CODE)

}