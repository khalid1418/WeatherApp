package com.plcoding.weatherapp.data.mapper

import android.util.Log
import com.plcoding.weatherapp.data.remote.WeatherDataDto
import com.plcoding.weatherapp.data.remote.WeatherDto
import com.plcoding.weatherapp.domain.weather.WeatherData
import com.plcoding.weatherapp.domain.weather.WeatherInfo
import com.plcoding.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private data class IndexedWeatherDate(
    val index:Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherMapper():Map<Int,List<WeatherData>>{
    return time.mapIndexed { index, time ->
        val pressure = pressureMsl[index]
        val humidity = relativeHumidity2m[index]
        val temperature = temperature2m[index]
        val weatherCode = weatherCode[index]
        val windSpeed = windSpeed10m[index]
        IndexedWeatherDate(
            index = index,
            data =   WeatherData(
                time = LocalDateTime.parse(time , DateTimeFormatter.ISO_DATE_TIME),
                pressure = pressure,
                humidity = humidity,
                temperatureCelsius = temperature,
                windSpeed = windSpeed,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index/24
    }.mapValues {
        it.value.map { it.data }
    }

}

fun WeatherDto.toWeatherInfo():WeatherInfo{
    val weatherDataMap = weatherData.toWeatherMapper()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
    val hour =  if (now.minute<30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return  WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}