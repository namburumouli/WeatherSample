package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapter.forecastAdapter
import com.example.weatherapp.comman.ForecastResource
import com.example.weatherapp.comman.Resource
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.view.fragment.RetryFragment
import com.example.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){


    private lateinit var binding: ActivityMainBinding
    private lateinit var clkRotate: Animation

    private val viewModel by viewModels<WeatherViewModel>()
    private var forecastRecyclerView:RecyclerView? =null
    private lateinit var foreCastAdapter: forecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        clkRotate = AnimationUtils.loadAnimation(this, R.anim.loading_rotate)
        binding.loading.startAnimation(clkRotate)

        viewModel.getWeather()


        foreCast()

        observerWeather()

    }

    private fun observerWeather() {
        viewModel.temperatureLiveData.observe(this) {
            binding.loading.clearAnimation()
            clkRotate.reset()
            binding.temparature.text = ((it.main?.temp?.toInt())?.minus(273)).toString() + "\u00B0"
            binding.city.text = it.name
            binding.loading.visibility = View.GONE
        }
    }

    private fun foreCast(){
        viewModel.getForecast(object : ForecastResource {
            override fun success(temperature: ArrayList<Double>, datelist: ArrayList<Int>) {
                forecastRecyclerView = binding.forecastRecyclerView
                foreCastAdapter = forecastAdapter(this@MainActivity,temperature,datelist)
                forecastRecyclerView!!.adapter = foreCastAdapter
                forecastRecyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)

            }

            override fun failure(error: String) {
                binding.loading.clearAnimation()
                clkRotate.reset()
                openFragment(RetryFragment())
                binding.loading.visibility = View.GONE

            }


        })
    }

    fun openFragment(fragment: Fragment){
        val transcation = supportFragmentManager.beginTransaction()
        transcation.replace(R.id.mainActivity,fragment)
        transcation.commit()
    }


}