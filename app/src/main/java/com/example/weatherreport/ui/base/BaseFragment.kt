package com.example.weatherreport.ui.base

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    fun getMainActivity(): MainActivity {
        return requireActivity() as MainActivity
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun getDisplayMetricsData(): Int {
        return getMainActivity().getDisplayMetricsData()
    }
}