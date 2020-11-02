package io.github.thefrsh.weather.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import javax.inject.Inject;

import io.github.thefrsh.weather.activity.MainActivity;
import io.github.thefrsh.weather.model.Weather;
import lombok.experimental.Delegate;

public class WeatherViewModelImpl implements WeatherViewModel
{
    @Delegate
    private Weather weather;

    @Inject
    public WeatherViewModelImpl()
    {

    }

    @Override
    public void onReturnButtonClick(View view)
    {
        Context context = view.getContext();
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity)(context)).finish();
    }
}
