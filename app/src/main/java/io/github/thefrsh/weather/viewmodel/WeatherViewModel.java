package io.github.thefrsh.weather.viewmodel;

import android.view.View;

import io.github.thefrsh.weather.model.Weather;

public interface WeatherViewModel
{
    void setWeather(Weather weather);

    void onReturnButtonClick(View view);

    String getMainText();

    String getDescriptionText();

    String getTemperatureText();

    String getFeelsLikeTemperatureText();

    String getPressureText();

    String getWindSpeedText();
}
