package com.example.weatherreport.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherreport.data.models.CurrentWeatherModel
import com.example.weatherreport.data.models.ForecastModel
import com.example.weatherreport.data.services.BaseResponse
import com.example.weatherreport.data.services.RepositorySDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeViewModel @Inject constructor(
    private val repositorySDK: RepositorySDK
) : ViewModel() {
    private val _currentWeatherData = MutableLiveData<BaseResponse<CurrentWeatherModel>>()
    val currentWeatherModel: LiveData<BaseResponse<CurrentWeatherModel>> = _currentWeatherData

    private val _foreCastWeatherData = MutableLiveData<BaseResponse<ForecastModel>>()
    val foreCastWeatherData: LiveData<BaseResponse<ForecastModel>> = _foreCastWeatherData
    var isChanged = ObservableField<Boolean>()

    init {
        getCurrentAndForeCastWeatherData("28.7041", "77.1025")//delhi lat long
        isChanged.set(false)
    }

    fun getCurrentAndForeCastWeatherData(lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentWeatherData.postValue(BaseResponse.Loading())
            try {
                val currWeatherRes = async { repositorySDK.getCurrentWeatherData(lat, long) }
                val forecastWeatherRes = async { repositorySDK.getForecastWeatherData(lat, long) }
                val res = awaitAll(currWeatherRes, forecastWeatherRes)

                _currentWeatherData.postValue(BaseResponse.Success(res.component1() as CurrentWeatherModel))
                _foreCastWeatherData.postValue(BaseResponse.Success(res.component2() as ForecastModel))
            } catch (e: Exception) {
                _currentWeatherData.postValue(BaseResponse.Error(e.message.toString()))
                _foreCastWeatherData.postValue(BaseResponse.Error(e.message.toString()))
            }

        }
    }
}