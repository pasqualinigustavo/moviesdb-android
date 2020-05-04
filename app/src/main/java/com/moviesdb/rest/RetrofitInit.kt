package com.moviesdb.rest

import com.moviesdb.rest.endpoint.TmdbApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class RetrofitInit<out T>(creator: () -> T) {

    companion object {
        val URL = "https://api.themoviedb.org/3/"
        val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        val DEFAULT_LANGUAGE = "pt-BR"
        val DEFAULT_REGION = "BR"
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
    private val mServicesPool = HashMap<String, Any?>()

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

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())
                .build()

        // Build the retrofit object
        mRetrofit = Retrofit.Builder()
                .baseUrl(RetrofitInit.URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(mHttpClient)
                .build()

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
