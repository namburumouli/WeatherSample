package com.example.weatherapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.view.MainActivity
import java.time.DayOfWeek
import java.util.*
import kotlin.collections.ArrayList

class forecastAdapter(
    private val mainActivity: MainActivity,
    private val temperature: ArrayList<Double>,
    private val datelist: ArrayList<Int>
) : RecyclerView.Adapter<forecastAdapter.ViewHolder>() {

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var day :TextView = itemView.findViewById(R.id.day)
        var temp:TextView = itemView.findViewById(R.id.temp)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_forecast_temp,parent,false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.temp.text = ((temperature[position].toInt()/8)-273).toString()+ "\u2103"
        val day:Int = if(datelist[position] != 0) datelist[position]  else 7
        holder.day.text = DayOfWeek.of(day).toString()
    }

    override fun getItemCount(): Int {
        return  4
    }
}