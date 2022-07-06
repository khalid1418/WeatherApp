package com.plcoding.weatherapp.data.repository

import android.util.Log
import com.plcoding.weatherapp.data.mapper.toWeatherInfo
import com.plcoding.weatherapp.data.remote.WeatherApi
import com.plcoding.weatherapp.domain.repository.WeatherRepository
import com.plcoding.weatherapp.domain.util.Resource
import com.plcoding.weatherapp.domain.weather.WeatherInfo
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
   private val api: WeatherApi
):WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
      return try {

          Resource.Success(
              data = api.getWeathereData(
                  lat = lat,
                  long = long
              ).toWeatherInfo()

          )


        }catch (e:Exception){
            e.printStackTrace()
          Resource.Error(e.message ?: "An unknown error occurred")

        }
    }
}