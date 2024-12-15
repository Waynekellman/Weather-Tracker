package com.example.weathertracker.data

import com.google.gson.JsonObject

data class CurrentWeather(var location: WLocation,
                          var current: WCurrent)
data class WLocation(var name:String)
data class WCurrent(var temp_f:Double,
                    var condition:WCondition,
                    var humidity: Double,
                    var feelslike_f: Double,
                    var uv: Double)
data class WCondition(var text: String, var icon:String)