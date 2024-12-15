package com.example.weathertracker.views

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.weathertracker.R
import com.example.weathertracker.ui.theme.WeatherTrackerTheme
import com.example.weathertracker.ui.theme.bottomTextColor
import com.example.weathertracker.ui.theme.boxBG
import com.example.weathertracker.ui.theme.topTextColor
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weathertracker.data.CurrentWeather
import com.example.weathertracker.data.SearchObject
import com.example.weathertracker.data.WCondition
import com.example.weathertracker.data.WCurrent
import com.example.weathertracker.data.WLocation
import com.example.weathertracker.ui.theme.searchColor
import com.example.weathertracker.ui.theme.searchTextColor

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherTrackerTheme {
        Greeting("Android")
    }
}

@Composable
fun boxContent(type:String, content: String) {
    val poppins = FontFamily(
        Font(R.font.poppins_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )
    )
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = type,
            color = topTextColor)
        Text(text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = bottomTextColor)
    }
}
@Composable
fun weatherBox(humidity: String, uv: String, feelsLike: String) {
    Box(modifier = Modifier
        .clip(shape = RoundedCornerShape(10.dp))
        .background(boxBG)
        .padding(10.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            boxContent(type = "Humidity", content = "$humidity%")
            Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp)){
                boxContent(type = "UV", content = uv)
            }
            boxContent(type = "Feels Like", content = "$feelsLike°")
        }
    }
}

@Composable
fun currentWeatherComp(webImage: String, contentDescription: String, location: String, temperature: String) {
    var loaded by remember {
        mutableStateOf(false)
    }
    val poppins = FontFamily(
        Font(R.font.poppins_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )
    )
    Column (
        modifier = Modifier
            .background(Color.White)
            .widthIn(max = 150.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(modifier = Modifier.fillMaxWidth(),
            model = "https:$webImage",
            contentScale = ContentScale.Fit,
            contentDescription = contentDescription,placeholder = painterResource(id = R.drawable.moon),
            error = painterResource(id = R.drawable.moon),
            fallback = painterResource(id = R.drawable.moon),
            onSuccess = {
                loaded = true
            })
        Row(modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
           Text(modifier = Modifier.padding(end = 5.dp),
               text = location,
               fontSize = 20.sp,
               fontWeight = FontWeight.Bold,
               style = TextStyle(Color.Black)
           )
            Icon(imageVector = Icons.Filled.Navigation,
                contentDescription = "",
                modifier = Modifier.rotate(30f),
                tint = Color.Black)
        }
        Text(text = "$temperature°",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(Color.Black))
    }
}

@Composable
fun searchBox(currentWeather: CurrentWeather) {
    var loaded by remember {
        mutableStateOf(false)
    }
    val poppins = FontFamily(
        Font(R.font.poppins_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )
    )
    Box(modifier = Modifier
        .clip(shape = RoundedCornerShape(10.dp))
        .background(boxBG)
        .padding(8.dp, top = 10.dp, bottom = 10.dp)
        .fillMaxWidth()
        .heightIn(max = 150.dp)) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = currentWeather.location.name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(Color.Black))
                Text(text = "${currentWeather.current.temp_f}°",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(Color.Black))
            }
            AsyncImage(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxHeight(),
                model = "https:${currentWeather.current.condition.icon}",
                contentScale = ContentScale.Fit,
                contentDescription = currentWeather.current.condition.text,
                placeholder = painterResource(id = R.drawable.moon),
                error = painterResource(id = R.drawable.moon),
                fallback = painterResource(id = R.drawable.moon),
                onSuccess = {
                    loaded = true
                } )

        }
    }
}
@Composable
fun searchBar(viewModel: HomeViewModel) {
    var text by rememberSaveable { mutableStateOf("") }
    val searchState by remember{viewModel.searchState}
    val clearSearch by remember {
        viewModel.clearSearch
    }
    if (clearSearch) {
        text = ""
        viewModel.clearSearch.value = false
    }
    if (text != "" ) {
        viewModel.searching.value = true
    }
    if (text != "") {
        viewModel.getSearchResults(text)
    }
    Column {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(15.dp))
        .background(boxBG)
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(boxBG),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ), modifier = Modifier
                        .background(boxBG),
                    value = text,
                    textStyle = TextStyle(Color.Black),
                    onValueChange = { text = it },
                    placeholder = {
                        Text(
                            text = "Search Location",
                            color = searchTextColor
                        )
                    })
                Icon(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable {
                            viewModel.getWeatherList()
                            viewModel.weatherState.value = viewModel.nullCurrentWeather
                            viewModel.searching.value = false
                        },
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Enter Search",
                    tint = searchColor
                )
            }
        }
        if (searchState.isNotEmpty() && text != "") {
            searchList(viewModel = viewModel)
        }
    }
}

@Composable
fun searchList(viewModel: HomeViewModel) {
    val autoList by remember {
        viewModel.searchState
    }
    val poppins = FontFamily(
        Font(R.font.poppins_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )
    )
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 300.dp)){
        items(autoList) { item ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 1.dp)
                .clickable {
                    viewModel.clearSearch.value = true
                    viewModel.searching.value = false
                    viewModel.searchState.value = emptyList()
                    viewModel.weatherStateList.value = mutableListOf()
                    viewModel.getCurrentWeatherResults(item.name)
                },
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(text = item.name + ", " + item.region,
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 10.dp))
            }
        }
    }
}

@Composable
fun appScreen(viewModel: HomeViewModel, sharedPref: SharedPreferences? = null) {
    val weather by remember{viewModel.weatherState}
    val search by remember{viewModel.searchState}
    val searching by remember {viewModel.searching}
    val weatherList by remember {viewModel.weatherStateList}
    Log.d("appScreen", "weather" + weather.current.condition.text)
    Log.d("appScreen", "search" + search.size)
    Log.d("appScreen", "searching" + searching.toString())
    Log.d("appScreen", "weatherList" + weatherList.size)

    Card (modifier = Modifier
        .background(Color.White)
        .padding(5.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(start = 20.dp, end = 20.dp, top = 30.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                searchBar(viewModel)
                if (weather.location.name == "" && search.isEmpty() && weatherList.isEmpty()) {
                    placeHolderScreen()
                } else if (weatherList.isNotEmpty()) {
                    searchBoxList(viewModel = viewModel)
                } else if (searching) {
                } else {
                    if (sharedPref != null){
                    with (sharedPref.edit()) {
                        putString("name", weather.location.name)
                        putFloat("temp_f", weather.current.temp_f.toFloat())
                        putFloat("humidity", weather.current.humidity.toFloat())
                        putFloat("feelslike_f", weather.current.feelslike_f.toFloat())
                        putFloat("uv", weather.current.uv.toFloat())
                        putString("text", weather.current.condition.text)
                        putString("icon", weather.current.condition.icon)
                        apply()
                    }
                    }
                    Box(modifier = Modifier.padding(top = 50.dp, bottom = 40.dp)) {
                        currentWeatherComp(
                            webImage = weather.current.condition.icon,
                            contentDescription = weather.current.condition.text,
                            location = weather.location.name,
                            temperature = weather.current.temp_f.toInt().toString()
                        )
                    }
                    weatherBox(
                        humidity = weather.current.humidity.toInt().toString(),
                        uv = weather.current.uv.toInt().toString(),
                        feelsLike = weather.current.feelslike_f.toInt().toString()
                    )

                }
            }
        }
    }
}

@Composable
fun searchBoxList(viewModel: HomeViewModel) {
    LazyColumn {
        items(viewModel.weatherStateList.value) {item->
            Card(modifier = Modifier.padding(top = 20.dp).clickable {
                viewModel.weatherStateList.value = mutableListOf()
                viewModel.searching.value = false
                viewModel.searchState.value = emptyList()
                viewModel.getCurrentWeatherResults(item.location.name)
            }) {
                searchBox(item)
            }
        }
    }
}

@Preview
@Composable
fun searchBarPrev() {
    val viewModel = HomeViewModel()
    viewModel.searchState.value = listOf(SearchObject("London", "1"),SearchObject("London", "2"),SearchObject("London", "3"),SearchObject("London", "4"),SearchObject("London", "5"),SearchObject("London", "6"))
    searchBar(viewModel = viewModel)
}

@Preview
@Composable
fun placeHolderScreen() {
    val poppins = FontFamily(
        Font(R.font.poppins_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )
    )
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 150.dp)
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "No City Selected",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold)
        Text(text = "Please Search For A City",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun appPreview() {
    val viewmodel = HomeViewModel()
    viewmodel.searchState.value = listOf(SearchObject("London", "1"))
    val weather = CurrentWeather(WLocation("London"), WCurrent(43.3, WCondition("Clear","//cdn.weatherapi.com/weather/64x64/night/113.png"),81.0,38.6,0.0))
    viewmodel.weatherState.value = weather
    appScreen(viewmodel, null)
}
@Preview
@Composable
fun searchBoxPrev() {
    val currentWeather = CurrentWeather(WLocation("Mumbai"),WCurrent(20.0, WCondition("", ""),20.0, 20.0, 4.0))
    searchBox(currentWeather)
}
@Preview
@Composable
fun weatherBoxPev() {
    weatherBox("20", "4", "38")
}

@Preview
@Composable
fun currentWeatherCompPrev() {
    currentWeatherComp(
        webImage = "//cdn.weatherapi.com/weather/64x64/day/116.png",
        contentDescription = "Partly cloudy",
        location = "Boston",
        temperature = "47"
    )
}

