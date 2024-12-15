package com.example.weathertracker.views

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertracker.data.CurrentWeather
import com.example.weathertracker.data.Search
import com.example.weathertracker.data.SearchObject
import com.example.weathertracker.data.WCondition
import com.example.weathertracker.data.WCurrent
import com.example.weathertracker.data.WLocation
import com.example.weathertracker.data.WeatherRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    val KEY = "3c249c10d8484006870174108241412"
    val repository = WeatherRepository()
    val searchState: MutableState<List<SearchObject>> = mutableStateOf(emptyList())
    val weatherState: MutableState<CurrentWeather> = mutableStateOf(
        CurrentWeather(
            WLocation(""),
            WCurrent(0.0, WCondition("", ""), 0.0, 0.0, 0.0)
        )
    )
    val weatherStateList: MutableState<MutableList<CurrentWeather>> =
        mutableStateOf(mutableListOf())
    val clearSearch: MutableState<Boolean> = mutableStateOf(false)
    val searching: MutableState<Boolean> = mutableStateOf(false)

    fun getSearchResults(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val value = repository.getSearchResults(KEY, search)
                searchState.value = value
            } catch (e: Exception) {
                Log.e("HomeViewModelsearch", "search exception", e)
            }
        }
    }

    fun getCurrentWeatherResults(location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val value = repository.getCurrentWeather(KEY, location)
                weatherState.value = value
                Log.d("getCurrentWeatherResults url", value.current.condition.icon)
            } catch (e: Exception) {
                Log.e("HomeViewModelweather", "weather exception", e)
            }
        }
    }

    fun getWeatherList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                weatherStateList.value = mutableListOf()
                searching.value = false
                for (search in searchState.value) {
                    val value = repository.getCurrentWeather(KEY, "id:${search.id.toString()}")
                    weatherStateList.value.add(value)
                }
                searchState.value = emptyList()
                clearSearch.value = true
            } catch (e: Exception) {
                Log.e("HomeViewModelweather", "weather list exception", e)
            }
        }
    }
}