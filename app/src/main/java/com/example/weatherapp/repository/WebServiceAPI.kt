package com.example.weatherapp.repository

import com.example.weatherapp.model.ForecastDataModel
import com.example.weatherapp.model.WeatherDataModel
import retrofit2.Response
import retrofit2.http.*

interface WebServiceAPI {


    @GET("weather")
    suspend fun getWeather(@Query("q") city:String, @Query("APPID") appID:String):Response<WeatherDataModel>

    @GET("forecast")
    suspend fun  getForecast(@Query("q") city:String, @Query("APPID") appID:String):Response<ForecastDataModel>
}