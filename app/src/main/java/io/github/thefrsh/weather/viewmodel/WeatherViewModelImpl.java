package io.github.thefrsh.weather.viewmodel;

import android.content.Intent;
import android.view.View;

import javax.inject.Inject;

import io.github.thefrsh.weather.activity.MainActivity;
import io.github.thefrsh.weather.model.Weather;

public class WeatherViewModelImpl implements WeatherViewModel
{
    private final Weather weather;

    @Inject
    public WeatherViewModelImpl(Weather weather)
    {
        this.weather = weather;
    }

    @Override
    public void onReturnButtonClick(View view)
    {
        var context = view.getContext();
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
