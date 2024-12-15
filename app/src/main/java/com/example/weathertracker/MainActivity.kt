package com.example.weathertracker

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weathertracker.ui.theme.WeatherTrackerTheme
import com.example.weathertracker.views.Greeting
import com.example.weathertracker.views.HomeViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.weathertracker.data.CurrentWeather
import com.example.weathertracker.data.WCondition
import com.example.weathertracker.data.WCurrent
import com.example.weathertracker.data.WLocation
import com.example.weathertracker.views.appScreen

class MainActivity : ComponentActivity() {

    val viewModel = HomeViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", "")
        val temp_f = sharedPref.getFloat("temp_f", 0f)
        val humidity = sharedPref.getFloat("humidity", 0f)
        val feelslike_f = sharedPref.getFloat("feelslike_f", 0f)
        val uv = sharedPref.getFloat("uv", 0f)
        val text = sharedPref.getString("text", "")
        val icon = sharedPref.getString("icon", "")
        if (name != "") {
            val weather = CurrentWeather(WLocation(name!!), WCurrent(temp_f.toDouble(), WCondition(text!!, icon!!), humidity.toDouble(), feelslike_f.toDouble(), uv.toDouble()))
            viewModel.weatherState.value = weather
        }
        setContent {
            WeatherTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    appScreen(viewModel, sharedPref)
                }
            }
        }
    }
}
