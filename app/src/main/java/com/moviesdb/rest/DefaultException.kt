package com.moviesdb.rest

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
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

    /** Identifies the event kind which triggered a [DefaultException].  */
    enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,
        /** A non-200 HTTP status code was received from the server.  */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED,

        UNAUTHORIZED,

        NOT_LINKED,

        /**
         * We are considering this kind of error as success, because there is no request problem
         * - TODO Investigate with more details
         */
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

    /** The request URL which produced the error.  */
    fun getUrl(): String? {
        return exceptionURL
    }

    /** Response object containing status code, headers, body, etc.  */
    fun getResponse(): Response<*>? {
        return exceptionResponse
    }

    /** The event kind which triggered this error.  */
    fun getKind(): Kind {
        return exceptionKind
    }

    /** The Retrofit this request was executed on  */
    fun getRetrofit(): Retrofit? {
        return exceptionRetrofit
    }

    /**
     * HTTP accountModel body converted to specified `type`. `null` if there is no
     * response.
     *
     */
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        val safeErrorBody = errorBody ?: return null

        val moshi = Moshi.Builder()
            .build()

        try {
            val jsonAdapter = try {
                moshi.adapter(type)
            } catch (jsonDataException: JsonDataException) {
                jsonDataException.printStackTrace()
                return null
            }
            jsonAdapter?.let {
               return jsonAdapter.fromJson(safeErrorBody)
            }
            return null
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
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