package io.github.thefrsh.weather.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.github.thefrsh.weather.R;
import io.github.thefrsh.weather.viewmodel.WeatherViewModel;

@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity
{
    @Inject
    WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }
}