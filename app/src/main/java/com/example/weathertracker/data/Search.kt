package com.example.weathertracker.data

import com.google.gson.JsonObject

data class Search(var search: List<SearchObject>)
data class SearchObject(var name: String, var region: String, var id: Int= -1)
