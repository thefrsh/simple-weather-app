package io.github.thefrsh.weather.service;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.github.thefrsh.weather.model.Weather;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@AndroidEntryPoint
public class WeatherWebServiceImpl implements WeatherWebService
{
    private static final String API_KEY = "f042b204d98c95586a6c4987242f2717";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final OkHttpClient httpClient;

    @Inject
    public WeatherWebServiceImpl(OkHttpClient client)
    {
        this.httpClient = client;
    }

    @Override
    public Weather getWeatherByCityName(String city) throws IOException
    {
        if (city == null)
        {
            throw new IllegalArgumentException("City name is null");
        }

        var url = BASE_URL + "?q=" + city + "&APPID=" + API_KEY;

        var request = new Request.Builder()
                .url(url)
                .get()
                .build();

        var weatherBuilder = Weather.builder();

        try(var response = httpClient.newCall(request).execute())
        {

        }

        return weatherBuilder.build();
    }
}
