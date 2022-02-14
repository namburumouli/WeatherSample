package com.example.weatherapp.di

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.comman.Constants
import com.example.weatherapp.repository.WebServiceAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                requestBuilder.addHeader(
                    "APPID", Constants.APPID
                )
                val request:Request = requestBuilder.build()
                val response = chain.proceed(request)
                response
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideWebServiceApi(client: OkHttpClient): WebServiceAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebServiceAPI::class.java)
    }


}