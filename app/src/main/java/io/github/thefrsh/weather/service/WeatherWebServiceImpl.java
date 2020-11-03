package io.github.thefrsh.weather.service;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.github.thefrsh.weather.model.Weather;
import io.github.thefrsh.weather.troubleshooting.CityNotFoundException;
import io.github.thefrsh.weather.viewmodel.WeatherViewModelImpl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherWebServiceImpl implements WeatherWebService
{
    private static final String API_KEY = "";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private static final String TAG = WeatherViewModelImpl.class.getName();

    private final OkHttpClient httpClient;

    @Inject
    public WeatherWebServiceImpl(OkHttpClient client)
    {
        this.httpClient = client;
    }

    @Override
    public Weather getWeatherByCityName(String city)
    {
        if (city == null)
        {
            throw new IllegalArgumentException("City name is null");
        }

        String url = BASE_URL + "?q=" + city + "&units=metric" + "&APPID=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Weather.WeatherBuilder weatherBuilder = Weather.builder();
        CountDownLatch latch = new CountDownLatch(1);

        httpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                Log.e(TAG, "Exception in http request: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        JSONObject jsonBody = new JSONObject(response.body().string());

                        JSONArray weatherArray = jsonBody.getJSONArray("weather");
                        JSONObject weatherObject = weatherArray.getJSONObject(0);

                        weatherBuilder.main(weatherObject.getString("main"));
                        weatherBuilder.description(weatherObject.getString("description"));

                        JSONObject mainObject = jsonBody.getJSONObject("main");

                        weatherBuilder.temperature(mainObject.getDouble("temp"));
                        weatherBuilder.feelsLikeTemperature(mainObject.getDouble("feels_like"));
                        weatherBuilder.pressure(mainObject.getInt("pressure"));

                        JSONObject windObject = jsonBody.getJSONObject("wind");
                        weatherBuilder.windSpeed(windObject.getDouble("speed"));

                        latch.countDown();
                    }
                    else
                    {
                        throw new CityNotFoundException();
                    }
                }
                catch (JSONException e)
                {
                    Log.e(TAG, "Error in JSON parsing: " + e.getMessage());
                }
            }
        });

        try
        {
            latch.await(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e)
        {
            Log.e(TAG, "Timeout error in http request: " + e.getMessage());
        }

        return weatherBuilder.build();
    }
}
