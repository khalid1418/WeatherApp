package com.plcoding.weatherapp.data.remote

import com.squareup.moshi.Json



data class WeatherDto(
	@field:Json(name = "hourly")
	val weatherData: WeatherDataDto
)

data class WeatherDataDto(

	@field:Json(name="pressure_msl")
	val pressureMsl: List<Double>,

	@field:Json(name="relativehumidity_2m")
	val relativeHumidity2m: List<Double>,

	@field:Json(name="temperature_2m")
	val temperature2m: List<Double>,

	@field:Json(name="weathercode")
	val weatherCode: List<Int>,

	@field:Json(name="windspeed_10m")
	val windSpeed10m: List<Double>,


	val time: List<String>
)
