package com.newsscraper.services

import com.google.gson.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

object ServiceFactory {

    private var httpClient: OkHttpClient? = null
    private var cacheHttpClient: OkHttpClient? = null
    private var noAuthHttpClient: OkHttpClient? = null

    private val gson: Gson
        get() = GsonBuilder()
            .setLenient()
            .registerTypeAdapter(
                LocalTime::class.java,
                JsonDeserializer<LocalTime> { json, _, _ -> LocalTime.parse(json?.asString) })
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonDeserializer<LocalDate> { json, _, _ ->
                    try {
                        LocalDate.parse(json.asString)
                    } catch (e: IllegalStateException) {
                        LocalDate.of(json.asJsonArray[0].asInt, json.asJsonArray[1].asInt, json.asJsonArray[2].asInt)
                    }
                })
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer<LocalDateTime> { json, _, _ -> LocalDateTime.parse(json.asString) })
            .registerTypeAdapter(
                Duration::class.java,
                JsonDeserializer<Duration> { json, _, _ -> Duration.parse(json.asString) })
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonSerializer<LocalDate> { src, _, _ -> JsonPrimitive(src.toString()) })
            .registerTypeAdapter(
                LocalTime::class.java,
                JsonSerializer<LocalTime> { src, _, _ -> JsonPrimitive(src.toString()) })
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonSerializer<LocalDateTime> { src, _, _ -> JsonPrimitive(src.toString()) })
            .registerTypeAdapter(
                Duration::class.java,
                JsonSerializer<Duration> { src, _, _ -> JsonPrimitive(src.toString()) })
            .serializeNulls()
            .create()

    internal fun <T> createRetrofitService(
        clazz: Class<T>,
        endPoint: String?,
        isRefreshToken: Boolean = false,
        authorizationHeader: Boolean = true
    ): T {
        if (endPoint == null) {
            throw NullPointerException("endPoint should not be null")
        }
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = if (authorizationHeader) createHttpClient(isRefreshToken) else createNoAuthHttpClient()

        val retrofit = Retrofit.Builder()
            .baseUrl(endPoint)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        return retrofit.create(clazz)
    }

    private fun createHttpClient(isRefreshToken: Boolean): OkHttpClient {
        return httpClient ?: OkHttpClient.Builder()
            .addNetworkInterceptor(AddHeaderInterceptor(isRefreshToken))
            .addInterceptor(createHttpLoggingInterceptor())
            .setTimeouts()
            .build()
    }

    private fun createNoAuthHttpClient(): OkHttpClient {
        return noAuthHttpClient ?: OkHttpClient.Builder()
            .addInterceptor(createHttpLoggingInterceptor())
            .setTimeouts()
            .build()
    }

    private fun OkHttpClient.Builder.setTimeouts(): OkHttpClient.Builder {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        return this
    }

    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    class AddHeaderInterceptor(private var isRefreshToken: Boolean) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            addAuthorizationHeader(builder, isRefreshToken)
            return chain.proceed(builder.build())
        }
    }

    private fun addAuthorizationHeader(builder: Request.Builder, isRefreshToken: Boolean) {
        builder.addHeader("Authorization", ServiceProvider.AUTHORIZATION_HEADER)
    }


}
