package com.moviesdb.data

import androidx.test.InstrumentationRegistry
import com.moviesdb.mock.MockRetrofitResponse
import com.moviesdb.model.GenreResponse
import com.moviesdb.model.Movie
import com.moviesdb.model.UpcomingMoviesResponse
import com.moviesdb.rest.APIComm
import com.moviesdb.rest.endpoint.TmdbApi
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import okhttp3.ResponseBody
import org.mockito.Mockito.mock
import retrofit2.http.Query
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set


class MockApiComm : TmdbApi {

    var messages =
            responseData<UpcomingMoviesResponse>("get_movies.json", UpcomingMoviesResponse::class.java) {
                result(KData<UpcomingMoviesResponse>::success)
            }

    override fun genres(apiKey: String, language: String): Observable<GenreResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun upcomingMovies(
            apiKey: String,
            language: String,
             page: Long,
             region: String
    ): Observable<UpcomingMoviesResponse> {
        return messages.get()
    }

    override fun movie(id: Long, apiKey: String, language: String): Observable<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * @param url - url of json to load locally
     * @param returnType - the Dto that the json ill be deserialised into
     * @param init - lambda that calls a method on KData class to set the method that will provide the result e.g { result(KData<ClaimantDetailsPollingResponseDto>::success) }
     */
    fun <T> responseData(url: String, returnType: Type, init: KData<T>.() -> Unit): KData<T> {
        val data = KData<T>(url, returnType)  // create the receiver object
        data.init()        // pass the receiver object to the lambda
        return data
    }

    fun <T> emptyResponseData(init: KData<T>.() -> Unit): KData<T> {
        val data = KData<T>("", ResponseBody::class.java)
        data.init()        // pass the receiver object to the lambda
        data.empty()
        return data
    }


    /*
     * Result actions, success, errors, network errors etc
     */
    fun <T> success(): MockApiComm.KData<T>.() -> Observable<T> {
        return MockApiComm.KData<T>::success
    }

    fun <T> error(): MockApiComm.KData<T>.() -> Observable<T> {
        return MockApiComm.KData<T>::error
    }

    fun <T> shutter(): MockApiComm.KData<T>.() -> Observable<T> {
        return MockApiComm.KData<T>::shutter
    }

    fun <T> serviceError(): MockApiComm.KData<T>.() -> Observable<T> {
        return MockApiComm.KData<T>::serviceError
    }

    fun <T> networkError(): MockApiComm.KData<T>.() -> Observable<T> {
        return MockApiComm.KData<T>::networkError
    }


    /*
     * Methods that create the DSL data side or helpers for it
     */

    class KData<T>(val url: String, private val returnType: Type) {
        private var toReplace: MutableMap<String, String> = HashMap()
        private var action: KData<T>.() -> Observable<T> = KData<T>::success
        private var statusCode: Int? = null


        fun replace(key: String, value: String) {
            toReplace[key] = value
        }

        fun result(action: KData<T>.() -> Observable<T>) {
            this.action = action
        }

        fun statusCode(code: Int) {
            statusCode = code
        }

        fun get(): Observable<T> {
            return action.invoke(this)
        }

        fun success(): Observable<T> {
            var json = loadJson()
            if (!toReplace.isEmpty()) {
                toReplace.forEach { (key, value) ->
                    json = json.replace(key, value, true)
                }
            }
            return MockRetrofitResponse.newSuccess<T>(toObject(json, returnType))
        }

        fun error(): Observable<T> {
            return MockRetrofitResponse.newError<T>(statusCode ?: 400)
        }

        fun shutter(): Observable<T> {
            return MockRetrofitResponse.newError<T>(statusCode ?: 503)
        }

        fun serviceError(): Observable<T> {
            return MockRetrofitResponse.newError<T>(statusCode ?: 500)
        }

        fun networkError(): Observable<T> {
            return MockRetrofitResponse.newNetworkError<T>()
        }

        fun empty(): Observable<ResponseBody> {
            return MockRetrofitResponse.newSuccess<ResponseBody>(mock(ResponseBody::class.java))
        }

        private fun <T> toObject(rawData: String, returnType: Type): T {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter<T>(returnType)
            return adapter.fromJson(rawData)!!
        }

        private fun loadJson(): String {
            val returnString = StringBuilder()
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(
                    InputStreamReader(
                        InstrumentationRegistry.getInstrumentation().context.assets.open(
                            url
                        ), "UTF-8"
                    )
                )
                // do reading, usually loop until end of file reading
                var line: String?
                line = reader.readLine()
                while (line != null) {
                    //process line
                    returnString.append(line)
                    line = reader.readLine()
                }
            } catch (e: IOException) {
                Timber.e(e)
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        Timber.e(e)
                    }
                }
            }
            return returnString.toString()
        }
    }
}

