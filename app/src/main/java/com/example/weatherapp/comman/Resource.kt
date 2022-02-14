package com.example.weatherapp.comman


interface Resource {

    fun success(temperature: Double, location: String)
    fun failure(error:String)
}

interface ForecastResource {

    fun success(temperature: ArrayList<Double>, datelist: ArrayList<Int>)
    fun failure(error: String)
}
