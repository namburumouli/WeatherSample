package com.example.weatherapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class WeatherDataModel(
    @SerializedName("coord"      ) var coord      : Coord?             = Coord(),
//    @SerializedName("weather"    ) var weather    : ArrayList<Weather> = arrayListOf(),
    @SerializedName("base"       ) var base       : String?            = null,
    @SerializedName("main"       ) var main       : Main?              = Main(),
    @SerializedName("visibility" ) var visibility : Int?               = null,
//    @SerializedName("wind"       ) var wind       : Wind?              = Wind(),
//    @SerializedName("clouds"     ) var clouds     : Clouds?            = Clouds(),
    @SerializedName("dt"         ) var dt         : Int?               = null,
//    @SerializedName("sys"        ) var sys        : Sys?               = Sys(),
    @SerializedName("timezone"   ) var timezone   : Int?               = null,
    @SerializedName("id"         ) var id         : Int?               = null,
    @SerializedName("name"       ) var name       : String?            = null,
    @SerializedName("cod"        ) var cod        : Int?               = null

){
    data class Main(
        @SerializedName("temp"       ) var temp      : Double? = null,
        @SerializedName("feels_like" ) var feelsLike : Double? = null,
        @SerializedName("temp_min"   ) var tempMin   : Double? = null,
        @SerializedName("temp_max"   ) var tempMax   : Double? = null,
        @SerializedName("pressure"   ) var pressure  : Int?    = null,
        @SerializedName("humidity"   ) var humidity  : Int?    = null
    )

    data class Coord (
        @SerializedName("lon" ) var lon : Double? = null,
        @SerializedName("lat" ) var lat : Double? = null
    )
}
