package io.github.thefrsh.weather.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.github.thefrsh.weather.R;
import io.github.thefrsh.weather.databinding.ActivityWeatherBinding;
import io.github.thefrsh.weather.model.Weather;
import io.github.thefrsh.weather.viewmodel.WeatherViewModel;

@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity
{
    private static final String WEATHER = "weather";
    private static final String CITY_NAME = "city_name";

    private String city;
    private ActivityWeatherBinding binding;

    @Inject
    WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Weather weather = getIntent().getParcelableExtra(WEATHER);

        weatherViewModel.setWeather(weather);

        city = getIntent().getStringExtra(CITY_NAME);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);

        binding.setWeatherViewModel(weatherViewModel);
        binding.setCity(city);
    }
}