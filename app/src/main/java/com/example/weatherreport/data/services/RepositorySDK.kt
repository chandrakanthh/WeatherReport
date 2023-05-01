package com.example.weatherreport.data.services


import com.example.weatherreport.data.models.CurrentWeatherModel
import com.example.weatherreport.data.models.ForecastModel
import com.example.weatherreport.ui.utils.Constants
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.jvm.Throws

class RepositorySDK(private val remoteService: RemoteService) {

    @Throws(Exception::class)
    suspend fun getCurrentWeatherData(lat: String, long: String): CurrentWeatherModel {
        return remoteService.httpClient.get {
            url(remoteService.getUrl(Constants.weatherEndPoint))
            url {
                parameters.append(
                    "lat", lat
                )
                parameters.append(
                    "lon", long
                )
                parameters.append(
                    "appid", Constants.appId
                )
                parameters.append(
                    "units", "metric"
                )
            }
            headers {
                remoteService.addHeaders(this)
            }
        }
    }

    @Throws(Exception::class)
    suspend fun getForecastWeatherData(lat: String, long: String): ForecastModel {
        return remoteService.httpClient.get {
            url(remoteService.getUrl(Constants.forecastEndPoint))
            parameter("lat", lat)
            parameter("lon", long)
            parameter("appid", Constants.appId)
            parameter("units", "metric")
            headers {
                remoteService.addHeaders(this)
            }
        }
    }
}