package com.example.weatherreport.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherreport.R
import com.example.weatherreport.data.services.BaseResponse
import com.example.weatherreport.databinding.FragmentWeatherHomeBinding
import com.example.weatherreport.ui.base.BaseFragment
import com.example.weatherreport.ui.utils.*
import com.example.weatherreport.ui.utils.Constants.TWO_MS
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.hypot

@RequiresApi(Build.VERSION_CODES.S)
@AndroidEntryPoint
class WeatherHomeFragment : BaseFragment(R.layout.fragment_weather_home) {
    private lateinit var weatherHomeBinding: FragmentWeatherHomeBinding
    private val weatherHomeVM: WeatherHomeViewModel by viewModels()
    private val forecastWeatherAdapter: ForecastWeatherAdapter by lazy {
        ForecastWeatherAdapter(
            requireContext(),
            arrayListOf()
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherHomeBinding = FragmentWeatherHomeBinding.bind(view)
        setOnObservers()
        initializeViews()
        setOnClickListeners()
    }

    private fun setOnClickListeners() = with(weatherHomeBinding) {
        fabBtn.setOnClickListener {
            weatherHomeVM.isChanged.get()?.takeIf { it }?.let {
                weatherHomeVM.getCurrentAndForeCastWeatherData(
                    "19.0760",
                    "72.8777"
                )//mumbai lat longs
            } ?: kotlin.run {
                weatherHomeVM.getCurrentAndForeCastWeatherData("28.7041", "77.1025")//delhi lat long

            }
        }


    }


    private fun initializeViews() = with(weatherHomeBinding) {
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        forecastListRV.layoutManager = llm
        forecastListRV.adapter = forecastWeatherAdapter
        arguments?.getString("profileUrl")?.let {
            Glide.with(context ?: requireContext()).load(it).placeholder(R.drawable.ic_person)
                .into(weatherHomeBinding.userIconIv)
        }

    }

    private fun setOnObservers() = with(weatherHomeVM) {
        currentWeatherModel.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    it.data?.let { result ->
                        weatherHomeBinding.weatherRes = result
                        updateUI()
                    }
                }
                is BaseResponse.Error -> {
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        }

        foreCastWeatherData.observe(viewLifecycleOwner) {
            it.data?.let { forecastRes ->
                forecastWeatherAdapter.updateList(forecastRes.list.distinctBy {
                    convertEpochToDayFormat(
                        it.dt ?: 0L
                    )
                })
            }
        }
    }

    private fun updateUI() = with(weatherHomeBinding) {
        if (weatherHomeVM.isChanged.get() == false) {
            mainBg.setBackGround(R.drawable.night_gradient_bg)
            cloudIV.show()
            secondCloudIV.show()
            dayBgImage.hide()
            startAnimUsingObjectAnim(cloudIV, getMainActivity().getDisplayMetricsData().toFloat())
            startAnimUsingObjectAnim(
                secondCloudIV,
                -getMainActivity().getDisplayMetricsData().toFloat()
            )
            cityImage.setImageFromDrawable(R.drawable.gateway_india)
            weatherHomeVM.isChanged.set(true)
        } else {
            mainBg.setBackGround(R.drawable.day_gradient_bg)
            cloudIV.hide()
            secondCloudIV.hide()
            dayBgImage.show()
            dayBgImage.post {
                val anim = ViewAnimationUtils.createCircularReveal(
                    dayBgImage, -dayBgImage.width, 0, 0f,
                    hypot(mainBg.width.toDouble(), mainBg.height.toDouble()).toInt().toFloat(),
                )
                anim.duration = TWO_MS
                anim.start()
            }
            cityImage.setImageFromDrawable(R.drawable.mumbai_icon)
            weatherHomeVM.isChanged.set(false)
        }
    }


}