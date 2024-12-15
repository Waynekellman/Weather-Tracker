package com.example.weathertracker.data

import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class WeatherRepository {

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun getCurrentWeather(key: String, query: String): CurrentWeather{
        val service = buildRetrofit().create(WeatherService::class.java)
        return service.getCurrentWeather(key, query)
    }
    suspend fun getSearchResults(key: String, query: String): List<SearchObject>{
        val service = buildRetrofit().create(WeatherService::class.java)
        return service.getSearchResults(key, query)
    }

}