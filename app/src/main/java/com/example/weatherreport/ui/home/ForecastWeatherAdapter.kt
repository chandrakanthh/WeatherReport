package com.example.weatherreport.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.R
import com.example.weatherreport.data.models.ForecastList
import com.example.weatherreport.databinding.ForecastListItemBinding
import com.example.weatherreport.ui.utils.convertEpochToDayFormat

class ForecastWeatherAdapter(private val context: Context, private var list: List<ForecastList>) :
    RecyclerView.Adapter<ForecastWeatherAdapter.ForecastWeatherViewHolder>() {
    private lateinit var adapterForecastWeatherBinding: ForecastListItemBinding
    private var selectedListItemPos = -1

    inner class ForecastWeatherViewHolder(val adapterForecastWeatherBinding: ForecastListItemBinding) :
        RecyclerView.ViewHolder(adapterForecastWeatherBinding.root) {
        fun bindData(dataListItem: ForecastList) = with(adapterForecastWeatherBinding) {
            dataListItem.let {
                it.apply {
                    this.day = convertEpochToDayFormat(it.dt ?: 0L)
                }
                adapterForecastWeatherBinding.forecastRes = it
                when (it.weather.first().main?.lowercase()) {
                    "clouds" -> {
                        tempTypeIV.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                context.resources,
                                R.drawable.ic_clouds,
                                null
                            )
                        )
                    }
                    "rain" -> {
                        tempTypeIV.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                context.resources,
                                R.drawable.ic_rainfall,
                                null
                            )
                        )
                    }
                    else -> {
                        tempTypeIV.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                context.resources,
                                R.drawable.ic_sun,
                                null
                            )
                        )
                    }
                }

            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<ForecastList>() {
        override fun areItemsTheSame(oldItem: ForecastList, newItem: ForecastList): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: ForecastList, newItem: ForecastList): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastWeatherAdapter.ForecastWeatherViewHolder {
        adapterForecastWeatherBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.forecast_list_item, parent, false
        )
        return ForecastWeatherViewHolder(adapterForecastWeatherBinding)
    }

    override fun onBindViewHolder(holder: ForecastWeatherViewHolder, position: Int) {
        val dataListItem = differ.currentList[holder.adapterPosition]
        holder.bindData(dataListItem)


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun updateList(updateRepoList: List<ForecastList>, selectedListItemPosition: Int = -1) {
        this.list = updateRepoList
        this.selectedListItemPos = selectedListItemPosition
        differ.submitList(updateRepoList)
        //notifyDataSetChanged()
    }


    private var selectedItemListener: ((pos: Int) -> Unit)? = null
    fun selectedItemListenerOnClick(listener: (pos: Int) -> Unit) {
        this.selectedItemListener = listener
    }
}