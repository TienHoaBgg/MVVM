@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.nth.mvvm.data.remote

import com.google.gson.*
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.nth.mvvm.BuildConfig
import com.nth.mvvm.common.Constants
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object ApiHelp {
    private fun createOkHttp(timeOut: Int = 1, vararg header: Pair<String, String>): OkHttpClient {
        val client = OkHttpClient.Builder()
        val logging = LoggingInterceptor.Builder()
                .loggable(Constants.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
        if (header.isNotEmpty()) {
            for (s in header) {
                logging.addHeader(s.first, s.second)
            }
        }
        client.addInterceptor(logging.build())
        return client
                .retryOnConnectionFailure(true)
                .connectTimeout(timeOut.toLong(), TimeUnit.MINUTES)
                .readTimeout(timeOut.toLong(), TimeUnit.MINUTES)
                .writeTimeout(timeOut.toLong(), TimeUnit.MINUTES)
                .build()
    }

    fun createRetrofit(timeOut: Int = 1, vararg header: Pair<String, String>): Retrofit {
        var gson = GsonBuilder()
        val formatDate = Constants.LIST_FORMAT_TIME
        for (format in formatDate) {
            gson = gson.registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
                override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
                    try {
                        return SimpleDateFormat(format, Locale.getDefault()).parse(json!!.asString)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    throw  JsonParseException("Unparseable date: \"" + json!!.asString + "  format: " + format)
                }
            })
        }

        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttp(timeOut, *header))
                .build()
    }

}