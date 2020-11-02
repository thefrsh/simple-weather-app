package io.github.thefrsh.weather.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import javax.inject.Inject;

import io.github.thefrsh.weather.activity.MainActivity;
import io.github.thefrsh.weather.model.Weather;

public class WeatherViewModelImpl implements WeatherViewModel
{
    private Weather weather;

    @Inject
    public WeatherViewModelImpl()
    {}

    @Override
    public void setWeather(Weather weather)
    {
        this.weather = weather;
    }

    @Override
    public String getMainText()
    {
        return weather.getMain();
    }

    @Override
    public String getDescriptionText()
    {
        return weather.getDescription();
    }

    @Override
    public String getTemperatureText()
    {
        return weather.getTemperature() + "°";
    }

    @Override
    public String getFeelsLikeTemperatureText()
    {
        return "Feels " + weather.getFeelsLikeTemperature() + "°";
    }

    @Override
    public String getPressureText()
    {
        return "Pressure " + weather.getPressure() + " hPa";
    }

    @Override
    public String getWindSpeedText()
    {
        return "Wind " + weather.getWindSpeed() + " m/s";
    }

    @Override
    public void onReturnButtonClick(View view)
    {
        Context context = view.getContext();
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity)(context)).finish();
    }
}
