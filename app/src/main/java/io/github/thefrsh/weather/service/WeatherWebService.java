package io.github.thefrsh.weather.service;

import io.github.thefrsh.weather.model.Weather;

public interface WeatherWebService
{
    Weather getWeatherByCityName(String city);
}
