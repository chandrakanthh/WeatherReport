package com.example.weatherreport.data.models

import io.ktor.util.date.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Transient

@kotlinx.serialization.Serializable
data class ForecastModel(

    @SerialName("cod") var cod: String? = null,
    @SerialName("message") var message: Int? = null,
    @SerialName("cnt") var cnt: Int? = null,
    @SerialName("list") var list: ArrayList<ForecastList> = arrayListOf(),
    @SerialName("city") var city: ForecastCity? = ForecastCity()

)

@kotlinx.serialization.Serializable
data class ForecastMain(

    @SerialName("temp") var temp: Double? = null,
    @SerialName("feels_like") var feelsLike: Double? = null,
    @SerialName("temp_min") var tempMin: Double? = null,
    @SerialName("temp_max") var tempMax: Double? = null,
    @SerialName("pressure") var pressure: Int? = null,
    @SerialName("sea_level") var seaLevel: Int? = null,
    @SerialName("grnd_level") var grndLevel: Int? = null,
    @SerialName("humidity") var humidity: Int? = null,
    @SerialName("temp_kf") var tempKf: Double? = null

)

@kotlinx.serialization.Serializable
data class ForecastWeather(

    @SerialName("id") var id: Int? = null,
    @SerialName("main") var main: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("icon") var icon: String? = null

)

@kotlinx.serialization.Serializable
data class ForecastClouds(

    @SerialName("all") var all: Int? = null

)

@kotlinx.serialization.Serializable
data class ForecastWind(

    @SerialName("speed") var speed: Double? = null,
    @SerialName("deg") var deg: Int? = null,
    @SerialName("gust") var gust: Double? = null

)

@kotlinx.serialization.Serializable
data class ForecastSys(

    @SerialName("pod") var pod: String? = null

)

@kotlinx.serialization.Serializable
data class ForecastList(

    @SerialName("dt") var dt: Long? = 0L,
    @SerialName("main") var main: ForecastMain? = ForecastMain(),
    @SerialName("weather") var weather: ArrayList<ForecastWeather> = arrayListOf(),
    @SerialName("clouds") var clouds: ForecastClouds? = ForecastClouds(),
    @SerialName("wind") var wind: ForecastWind? = ForecastWind(),
    @SerialName("visibility") var visibility: Int? = null,
    @SerialName("pop") var pop: Double? = null,
    @SerialName("sys") var sys: ForecastSys? = ForecastSys(),
    @SerialName("dt_txt") var dtTxt: String? = null,

    @Transient
    var day: String? = null

)


@kotlinx.serialization.Serializable
data class ForecastCity(

    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("coord") var coord: ForecastCoord? = ForecastCoord(),
    @SerialName("country") var country: String? = null,
    @SerialName("population") var population: Int? = null,
    @SerialName("timezone") var timezone: Int? = null,
    @SerialName("sunrise") var sunrise: Int? = null,
    @SerialName("sunset") var sunset: Int? = null

)

@kotlinx.serialization.Serializable
data class ForecastCoord(

    @SerialName("lat") var lat: Double? = null,
    @SerialName("lon") var lon: Double? = null

)