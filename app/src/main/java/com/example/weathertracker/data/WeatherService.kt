package com.example.weathertracker.data

import android.app.appsearch.SearchResults
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    suspend fun getCurrentWeather(@Query("key") key: String, @Query("q") query: String): CurrentWeather

    @GET("search.json")
    suspend fun getSearchResults(@Query("key") key: String, @Query("q") query: String): List<SearchObject>

}