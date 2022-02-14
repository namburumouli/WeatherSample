package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.comman.Constants
import com.example.weatherapp.comman.ForecastResource
import com.example.weatherapp.comman.Resource
import com.example.weatherapp.model.WeatherDataModel
import com.example.weatherapp.repository.WebServiceAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val retrofitWebService: WebServiceAPI
) : ViewModel() {

    private val _temperatureLiveData: MutableLiveData<WeatherDataModel> by lazy { MutableLiveData() }
    val temperatureLiveData:LiveData<WeatherDataModel> get() = _temperatureLiveData


    private var temperature by Delegates.notNull<Double>()
    private lateinit var location: String
    private val tempList: ArrayList<Double> = ArrayList()
    private val datelist:ArrayList<Int> = ArrayList()



    fun getWeather() {

        viewModelScope.launch {
            try {
                val service = retrofitWebService
                val response = service.getWeather("visakhapatnam", Constants.APPID)
                    when {
                        response.isSuccessful -> {
                            _temperatureLiveData.value = response.body()

                        }
                        else -> {
                            Log.i("viewmodel", "error")
                        }
                    }



            } catch (ex: Exception) {
                Log.i("viewmodel", ex.toString())
            }
        }
    }


    fun getForecast(param: ForecastResource) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formate = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        viewModelScope.launch {
            try {
                val service = retrofitWebService
                val response = service.getForecast("visakhapatnam", Constants.APPID)
                    when {
                        response.isSuccessful -> {
                            response.body()?.list?.forEach {
                                if (sdf.parse(it.dtTxt).date != sdf.parse(currentDate).date) {
                                    it.main?.temp?.let { it1 -> tempList.add(it1) }
                                    datelist.add(formate.parse(it.dtTxt).day)
                                    Log.i("forecastData", tempList.toString())
                                }

                            }
                            var avgTempList = tempList.chunked(8) {
                                it.sum()
                            }
                            val set:Set<Int> =  LinkedHashSet(datelist)
                            datelist.clear()
                            datelist.addAll(set)
                            param.success(avgTempList as ArrayList<Double>,datelist)

                        }
                        else -> {
                            Log.i("viewmodelforecast", "error")
                            param.failure(response.message())

                        }

                    }


            } catch (ex: Exception) {
                Log.i("viewmodel forecast", ex.toString())
                param.failure(ex.toString())
            }
        }
    }

}

