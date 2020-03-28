package com.moviesdb.rest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.moviesdb.rest.endpoint.TmdbApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class RetrofitInit<out T>(creator: () -> T) {

    companion object {

    }

    private var creator: (() -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!()
                instance = created
                creator = null
                created
            }
        }
    }
}

class APIComm @Inject constructor() {

    lateinit var mRetrofit: Retrofit
    lateinit var mHttpClient: OkHttpClient
    lateinit var gson: Gson
    private val mServicesPool = HashMap<String, Any?>()
    var URL_BASE = TmdbApi.URL

    companion object : RetrofitInit<APIComm>(::APIComm)

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // Add the interceptor to OkHttpClient
        mHttpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build()


        // GSON Parser
        gson = GsonBuilder()
                .create()

        // Build the retrofit object
        mRetrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpClient)
                .build()

    }

    fun gson(): Gson {
        return gson
    }

    fun <Any> create(service: Class<Any>): kotlin.Any? {
        val key = service.simpleName
        if (!mServicesPool.containsKey(key)) {
            mServicesPool.put(key, mRetrofit.create(service))
        }
        return mServicesPool.get(key)
    }

    fun tmdbEndpoint(): TmdbApi {
        return create(TmdbApi::class.java) as TmdbApi
    }
}
