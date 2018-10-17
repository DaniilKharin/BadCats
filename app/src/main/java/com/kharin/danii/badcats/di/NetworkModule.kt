package com.kharin.danii.badcats.di

import com.kharin.danii.badcats.BuildConfig
import com.kharin.danii.badcats.CatsApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(accessTokenProvidingInterceptor())
        .addInterceptor(loggingInterceptor())
        .build()

    private fun accessTokenProvidingInterceptor() = Interceptor { chain ->
        val accessToken = BuildConfig.X_API_KEY
        chain.proceed(chain.request().newBuilder()
            .addHeader("x-api-key", accessToken)
            .build())
    }

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): CatsApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL+BuildConfig.API_VERSION)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatsApi::class.java)

}